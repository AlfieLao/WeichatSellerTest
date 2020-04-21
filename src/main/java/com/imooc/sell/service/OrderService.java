package com.imooc.sell.service;

import com.imooc.sell.dataTransformObject.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    /** 创建订单*/
    OrderDto create(OrderDto orderDto);

    /** 查询单个订单*/
    OrderDto findOne(String orderId);

    /** 查询订单列表 用户*/
    Page<OrderDto> findList(String buyerOpenID, Pageable pageable);

    /** 查询订单列表 商家*/
    Page<OrderDto> findList( Pageable pageable);

    /** 取消订单*/
    OrderDto cancel(OrderDto orderDto);

    /** 完结订单*/
    OrderDto finish(OrderDto orderDto);

    /** 支付订单*/
    OrderDto paid(OrderDto orderDto);
}
