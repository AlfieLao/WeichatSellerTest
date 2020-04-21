package com.imooc.sell.dataObject.Mapping;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired ProductCategoryMapper  productCategoryMapper;

    @Test
    public void insertByMap() throws  Exception{
        Map<String,Object> map =new HashMap<>();
        map.put("category_name","测试类型");
        map.put("category_type","15");
        int i = productCategoryMapper.insertByMap(map);
        Assert.assertTrue("Mybatis failed ",i==1);
    }
}