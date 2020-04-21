package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

/** 商品包含类目*/
@Data
public class ProductVO {

    @JsonProperty("name")  /** 返回给前端的时候就是name了*/
    private  String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
