package com.imooc.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/** 支付配置*/
@Component
public class WeChatPayConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayServiceImpl(WxPayH5Config wxPayH5Config){
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
//        bestPayService.setWxPayH5Config( wxPayH5Config());
        return  bestPayService;
    }

    @Bean
    private WxPayH5Config wxPayH5Config(){
        WxPayH5Config config =new WxPayH5Config();
        config.setAppId(wechatAccountConfig.getMpAppId());
        config.setAppSecret(wechatAccountConfig.getMpAppSecret());
        config.setMchId(wechatAccountConfig.getMchId());
        config.setMchKey(wechatAccountConfig.getMchKey());
        config.setKeyPath(wechatAccountConfig.getKeyPath());
        config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return config;
    }
}
