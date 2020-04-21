package com.imooc.sell.service;

import com.imooc.sell.dataTransformObject.OrderDto;

/*
 * @Description:推送消息
 * @Author: AlfieLao
 * @Date: 2020/4/14 23:45
 **/
public interface PushMessage {

    void orderStatus(OrderDto orderDto);
}
