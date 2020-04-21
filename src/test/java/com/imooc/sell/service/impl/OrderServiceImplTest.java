package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String buyerOrderId = "1101110123";

    private final String ORDER_ID ="156649006097919855";

    private final String BUYER_OPENID ="1101110123";

    @Test
    @Transactional
    void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("liu");
        orderDto.setBuyerAddress("美国");
        orderDto.setBuyerOpenid(buyerOrderId);
        orderDto.setBuyerPhone("123456789012");

        List<OrderDetail> list = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123459");
        orderDetail.setProductQuantity(10);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("123456");
        orderDetail2.setProductQuantity(1);
        list.add(orderDetail);
        list.add(orderDetail2);

        orderDto.setOrderDetailList(list);
        OrderDto result = orderService.create(orderDto);
        log.info("订单 result ={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    void findOne() {
         OrderDto orderDto = orderService.findOne(ORDER_ID);
         log.info("查询单个单据{}",orderDto);
         Assert.assertNotNull(orderDto);
        Assert.assertNotNull(orderDto.getOrderDetailList());
         Assert.assertNotEquals(0, orderDto.getOrderDetailList());
    }

    @Deprecated
    @Test
    void findList() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDto> rusult = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0, rusult.getTotalElements());
    }

    @Deprecated
    @Test
    void findListForSeller() {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDto> result = orderService.findList(request);
//        Assert.assertNotEquals(0, result.getTotalElements());
        Assert.assertTrue("查询所有订单列表 不存在",result.getTotalElements()>0);
    }

    @Test
    void cancel() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    void paid() {

        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.paid(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }
}