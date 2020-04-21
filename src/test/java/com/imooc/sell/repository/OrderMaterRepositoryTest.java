package com.imooc.sell.repository;

import com.imooc.sell.dataObject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMaterRepositoryTest {

    @Autowired
    OrderMaterRepository repository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("liu");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("火星");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(2.3));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Deprecated
    @Test
    public void findByBuyerOpenId() throws Exception{
        PageRequest request = new PageRequest(0, 1);//这里分页从0开始
//        PageRequest request = new PageRequest(1, 3);//如果是这样的，数据库小于4条数据，这里查出来是0
        Assert.assertNotEquals(0,
                repository.findByBuyerOpenid("110110",request)
                        .getTotalElements()) ;
    }

}