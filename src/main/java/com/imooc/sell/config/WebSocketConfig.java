package com.imooc.sell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/*
 * @Description:websocket配置
 * @Author: AlfieLao
 * @Date: 2020/4/15 23:54
 **/
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter1(){
        return new ServerEndpointExporter();
    }
}
