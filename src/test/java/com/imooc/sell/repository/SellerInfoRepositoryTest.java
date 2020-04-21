package com.imooc.sell.repository;

import com.imooc.sell.dataObject.SellerInfo;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class SellerInfoRepositoryTest {
@Autowired
private SellerInfoRepository repository;

    @Test
    void  save(){
        SellerInfo sellerInfo =new SellerInfo();
        sellerInfo.setOpenid("lyh");
        sellerInfo.setPassword("xx");
        sellerInfo.setSellerId("lyh");
        sellerInfo.setUsername(KeyUtil.genUniqueKey());
        repository.save(sellerInfo);
    }

    @Test
    void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("lyh");
        Assert.assertTrue("找不到",sellerInfo!=null);

    }
}