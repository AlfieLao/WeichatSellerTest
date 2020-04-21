package com.imooc.sell.enums;


import lombok.Getter;

@Getter
public enum  ProductStatusEnum implements  CodeEnums{
    /** 上架*/
    UP(0,"上架"),
    /** 下架*/
        DOWN(1,"下架");

        private Integer code;

        private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
