package com.imooc.sell.service.impl;

import com.imooc.sell.dataObject.SellerInfo;
import com.imooc.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import org.junit.Test;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerServiceImplTest {


    @Autowired
    SellerService sellerService;

    @Test
   public void findSellerInfoByOpenid() {
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid("lyh");
        Assert.assertTrue("sellerService.findSellerInfoByOpenid失败",sellerInfo.getOpenid().equals("lyh"));
    }
}