package com.imooc.sell.dataObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.utils.EnumsUtil;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate(value = true)
@DynamicInsert(value = false)
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;

    /** 名字*/
    private  String  productName;

    /** 单价*/
    private BigDecimal productPrice;

    /** 库存*/
    private Integer productStock;

    /** 描述*/
    private String productDescription;

    /** 小图*/
    private  String productIcon;

    /** 状态 0正常 1下架*/
    private  Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号*/
    private  Integer categoryType;

//        @CreationTimestamp
//    @CreatedDate
    @Column(updatable = false ,insertable = false)
    private Date createTime;

        @UpdateTimestamp
    private Date updateTime;

    public ProductInfo(String productName, BigDecimal productPrice, String productDescription) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }



    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumsUtil.getByCode(productStatus,ProductStatusEnum.class);
    }

    public ProductInfo() {
    }
}
