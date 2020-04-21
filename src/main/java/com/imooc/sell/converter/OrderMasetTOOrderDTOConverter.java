package com.imooc.sell.converter;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dataTransformObject.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMasetTOOrderDTOConverter {

    public static OrderDto convert(OrderMaster orderMaster) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return  orderDto;
    }

    public static List<OrderDto> convert(List<OrderMaster> orderMasters){
       return orderMasters.stream()
//                .map(OrderMasetTOOrderDTOConverter::convert)
                .map(t-> convert(t))
                .collect(Collectors.toList());
    }
}
