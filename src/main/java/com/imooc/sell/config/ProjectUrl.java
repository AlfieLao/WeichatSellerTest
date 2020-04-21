package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties( prefix = "projecturl")
public class ProjectUrl {

    //微信公众平台url
    public  String weChatMpAuthorize;
    //开放平台
    public  String weChatOpenAuthorize;
    //点餐系统
    public  String sell;
}
