package com.imooc.sell.service.impl;

import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
       return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid,orderId);
        if (orderDto == null){
            log.error("【取消订单】：找不到订单orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDto);
    }

    private OrderDto checkOrderOwner(String openid, String orderId){
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            return null;
        }
        if (!orderDto.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】：订单的openid不一致openid={}",openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
