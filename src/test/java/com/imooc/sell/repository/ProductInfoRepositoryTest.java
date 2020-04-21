package com.imooc.sell.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.imooc.sell.dataObject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {
    @Autowired
    private  ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo("皮蛋瘦肉粥",new BigDecimal( 5.5),"测试");
        productInfo.setProductId("123456");
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://xxx,jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() throws Exception{
        List<ProductInfo> list = repository.findByProductStatus(0);
        Assert.assertNotEquals(0, list.size());
    }
}