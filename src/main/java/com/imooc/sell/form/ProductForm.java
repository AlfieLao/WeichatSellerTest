package com.imooc.sell.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.utils.EnumsUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品模型
 * */
@Data
public class ProductForm {

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

//    /** 状态 0正常 1下架*/
//    private  Integer productStatus;

    /** 类目编号*/
    private  Integer categoryType;

    public ProductForm(String productName, BigDecimal productPrice, String productDescription) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }


    public ProductForm() {
    }
}