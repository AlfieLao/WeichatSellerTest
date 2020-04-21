package com.imooc.sell.service.impl;

import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.imooc.sell.utils.SellException;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        /** 调用统一下单API*/
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付-发起支付】 request={}", JsonUtil.toJson( payRequest));
        PayResponse payResponse =  bestPayService.pay(payRequest);
        log.info("【微信支付-发起支付】 response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.要验证签名 是不是微信发来的  SDK已做了
        //2.检查支付状态  SDK已做了
        //3.检查支付金额
        //4.支付人（下单人  == 支付人） 这里不限制
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付- 异步通知】 response={}",JsonUtil.toJson(payResponse));

        /** 修改订单支付状态*/
        OrderDto orderDto = orderService.findOne(payResponse.getOrderId());
        if(orderDto ==null) {
            log.error("【微信支付- 异步通知】订单不存在  orderId={}",payResponse.getOrderId());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
//这种判断有误差 不行
//        if (orderDto.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount()))!=0){
        if (!MathUtil.equals(orderDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付- 异步通知】订单金额不一致  orderId={},微信金额={}，系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDto.getOrderAmount()
            );
            throw  new SellException(ResultEnum.WECHAT_NOTIFY_MONEY_VERIFY_ERROR);
        }
        orderService.paid(orderDto);

        return payResponse;
    }

    /** 退款*/
    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request ={}",JsonUtil.toJson(refundRequest) );
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response ={}",JsonUtil.toJson(refundResponse) );
        return refundResponse;
    }
}
