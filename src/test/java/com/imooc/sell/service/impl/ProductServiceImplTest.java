package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dataTransformObject.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl service;

    @Test
    void findOne() {
        ProductInfo productInfo = service.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    void findAllUp() {
        List<ProductInfo> list = service.findAllUp();
        Assert.assertNotEquals(0,list.size());
    }

    @Deprecated //取消放弃方法警告
    @Test
    void findAll() {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfos = service.findAll(request);//total指所有符合的条件
        log.info(productInfos.getTotalElements()+"test");
        Assert.assertNotEquals(0, productInfos.getTotalElements());//getTotalElements指总共查出的数目，不是size*page
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo("皮蛋瘦肉粥3",new BigDecimal( 6.5),"不用放盐");
        productInfo.setProductId("123459");
        productInfo.setProductStock(150);
        productInfo.setProductIcon("http://xxx,jpg");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(2);
        ProductInfo result = service.save(productInfo);
//        productInfo.setUpdateTime(new Date(.));
        Assert.assertNotNull(result);
    }

    @Test
    void increaseStork()
    {
        List<CartDTO> list = new ArrayList<>();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId("001");
        cartDTO.setProductQuantity(1);
        list.add(cartDTO);
        service.increaseStork(list);
    }

    @Test
    void  onSale(){
        ProductInfo productInfo = service.onSale("001");
        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }
}