package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryServiceImplTest {

    @Autowired
    private  CategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategories = categoryService.findAll();
        Assert.assertNotEquals(0 ,productCategories.size());
    }

    @Test
    void fingByCategoryType() {
        List<ProductCategory> productCategories = categoryService.fingByCategoryType(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0,productCategories.size());

    }

    @Test
    @Transactional
    void save() {
        ProductCategory productCategory = new ProductCategory("男生不爱", 7);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);

    }
}