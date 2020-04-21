package com.imooc.sell.dataObject;


import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单ID.*/
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEM.getCode();

    /**  支付状态*/
    private  Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间*/
    private Date createTime;

    /** 更新时间*/
    private Date updateTime;

//    /** 订单细明*/
//    @Transactional  //不会进数据库查询
//    private List<OrderDetail> orderDetailList;

    public OrderMaster(){}
}
