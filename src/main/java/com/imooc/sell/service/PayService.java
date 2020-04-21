package com.imooc.sell.service;

import com.imooc.sell.dataTransformObject.OrderDto;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import org.springframework.stereotype.Service;

/** 支付*/
public interface PayService {

    PayResponse create(OrderDto orderDto);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDto orderDto);
}
