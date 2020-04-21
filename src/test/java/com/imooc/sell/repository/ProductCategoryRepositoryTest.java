package com.imooc.sell.repository;

import com.imooc.sell.dataObject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
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
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository mRepository;

    @Test
    public void findOneByIDTest(){
        /** 这里需要默认的构造方法*/
        ProductCategory productCategory = mRepository.findById(1).orElse(null);
        log.info(productCategory.toString());
    }

    @Test
    @Transactional //这里的作用是测试数据不会对数据库造成影响
    public void saveTest(){
        /**这种是直接替换 */
//        ProductCategory p1 = mRepository.findById(2).get();
//        p1.setCategoryType(3);
//        mRepository.save(p1);
        /** 这种是查出数据库的数据在更新 */
        ProductCategory productCategory = new ProductCategory("男生最爱",5);
        ProductCategory result = mRepository.save(productCategory);
        Assert.assertNotNull(result);//Assert.assertNotEquals(null, result);
    }


    @Test
    public void findCategoryTypeTest(){
        List<Integer> list = Arrays.asList(2,3,5);
        List<ProductCategory> result = mRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }

}