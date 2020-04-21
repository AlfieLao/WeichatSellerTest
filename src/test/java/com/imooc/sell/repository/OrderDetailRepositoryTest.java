package com.imooc.sell.repository;

import com.imooc.sell.dataObject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Transactional
    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456787");
        orderDetail.setOrderId("111112");
        orderDetail.setProductIcon("http://baidu.jpg");
        orderDetail.setProductId("1234");
        orderDetail.setProductName("皮蛋瘦肉粥");
        orderDetail.setProductPrice(new BigDecimal(3.5));
        orderDetail.setProductQuantity(20);

        OrderDetail result =repository.save(orderDetail);
        Assert.assertNotNull(result);
    }



    @Test
    public void findByOrderId()throws Exception{
        List<OrderDetail> list = repository.findByOrderId("111112");
        Assert.assertNotEquals(0, list.size());

    }

}