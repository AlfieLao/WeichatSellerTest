package com.imooc.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.utils.SellException;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormToOrderDTOConverter {

    public static OrderDto convert(OrderForm orderForm){
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetails = new ArrayList<>();
        try{
            Gson gson = new Gson();
            orderDetails = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){
                    }.getType());
        }catch (Exception e){
            log.error("【转换对象】错误 jsonString={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;

    }
}
//<!-- 谷歌json转换工具-->
//<dependency>
//<groupId>com.google.code.gson</groupId>
//<artifactId>gson</artifactId>
//</dependency>