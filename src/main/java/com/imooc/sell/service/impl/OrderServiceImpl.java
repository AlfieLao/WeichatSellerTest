package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMasetTOOrderDTOConverter;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dataTransformObject.CartDTO;
import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMaterRepository;
import com.imooc.sell.service.*;
import com.imooc.sell.utils.KeyUtil;
import com.imooc.sell.utils.SellException;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMaterRepository orderMaterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WebSocketTest webSocketTest;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        //1.查询商品（数量 价格）
        for(OrderDetail orderDetail: orderDto.getOrderDetailList())
        {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo== null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
//            if (orderDetail.getProductQuantity() > productInfo.getProductStock()){//这里不用判断库存，用扣库存的接口判断
            //2.计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal( orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //下面这句话可以不可以忽略某些不转换
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //3.写入数据库（orderMaster OrderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEM.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaterRepository.save(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList =  orderDto.getOrderDetailList()
                .stream()
                .map(t->new CartDTO(t.getProductId(), t.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStork(cartDTOList);

        //5.发生websocket消息
        webSocketTest.sendMessage("有新订单:"+orderId);
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster  = orderMaterRepository.findById(orderId).orElse(null);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> details = orderDetailRepository.findByOrderId(orderId);
        if (details ==null&&details.size()<1)
        {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(details);
        return  orderDto;
    }


    @Override
    @Deprecated
    public Page<OrderDto> findList(String buyerOpenID, Pageable pageable) {
//        PageRequest pageRequest = new PageRequest(1,10);
        Page<OrderMaster> result =  orderMaterRepository.findByBuyerOpenid(buyerOpenID,pageable);

        List<OrderDto> orderDtoList = OrderMasetTOOrderDTOConverter.convert(result.getContent());
        return new PageImpl<OrderDto>(orderDtoList,pageable,result.getTotalElements());

    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> result =  orderMaterRepository.findAll(pageable);

        List<OrderDto> orderDtoList = OrderMasetTOOrderDTOConverter.convert(result.getContent());
        return new PageImpl<OrderDto>(orderDtoList,pageable,result.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEM.getCode())){
            log.info("【取消订单】订单状态不正确，orderId={}，orderStatus={}"
                    ,orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderResult = orderMaterRepository.save(orderMaster);
        if (orderResult == null) {
            log.info("【取消订单】更新失败，order={}",orderDto);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.info("【取消订单】无此单据细明，orderid={}",orderDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDto.getOrderDetailList().stream()
                .map(t-> new CartDTO(t.getProductId(),t.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStork(cartDTOList);
        //如果支付了，退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            payService.refund(orderDto);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //订单状态
        if (orderDto.getOrderStatus() != OrderStatusEnum.NEM.getCode()){
            log.error("【完结订单】订单状态错误，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new  SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMaterRepository.save(orderMaster);
        if (result == null){
            log.error("【完结订单】 更新失败，order={}",orderDto);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模板消息
        pushMessage.orderStatus(orderDto);
        return orderDto;
    }

    @Transactional
    @Override
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if (orderDto.getOrderStatus() != OrderStatusEnum.NEM.getCode()){
            log.error("【支付订单完成】订单状态错误，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new  SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (orderDto.getPayStatus() != PayStatusEnum.WAIT.getCode()){
            log.error("【支付订单完成】订单支付状态不正确，order={}",orderDto);
            throw new  SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMaterRepository.save(orderMaster);
        if (result == null){
            log.error("【支付订单完成】 更新失败，order={}",orderDto);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }
}
