package com.imooc.sell.dataTransformObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.utils.EnumsUtil;
import com.imooc.sell.utils.serializer.DateToLongSerializer;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//为空的不返回
//@JsonInclude(JsonInclude.Include.NON_NULL)//为空的字段不返回
public class OrderDto {//DOT的目的还可以携带Details避免ORM的映射
    /** 订单ID.*/
    private String orderId;

    /** 买家名字.*/
    private String buyerName;

    /** 买家手机号.*/
    private String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家微信Openid.*/
    private String buyerOpenid;

    /** 买家金额*/
    private BigDecimal orderAmount;

    /**  订单状态*/
    private Integer orderStatus;

    /**  支付状态*/
    private  Integer payStatus;

    /** 创建时间*/
    @JsonSerialize(using = DateToLongSerializer.class)//自动转化为时间戳
    private Date createTime;

    /** 更新时间*/
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
//        return OrderStatusEnum.getString(orderStatus);
        return EnumsUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumsUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
