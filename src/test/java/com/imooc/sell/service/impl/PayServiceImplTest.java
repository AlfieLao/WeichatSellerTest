package com.imooc.sell.service.impl;

import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    void create() {
        OrderDto orderDto = orderService.findOne("156692370306288449");
        payService.create(orderDto);
    }

    @Test
    public void RefundTest(){
        OrderDto orderDto = orderService.findOne("156692370306288450");
        payService.refund(orderDto);
    }
}