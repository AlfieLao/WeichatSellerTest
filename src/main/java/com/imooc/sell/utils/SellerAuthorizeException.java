package com.imooc.sell.utils;

import com.imooc.sell.enums.ResultEnum;

/*
 * @Description:登录检验失败异常
 * @Author: AlfieLao
 * @Date: 2020/4/12 16:20
 **/
public class SellerAuthorizeException extends RuntimeException{ //RuntimeException有msg属性

    private Integer code;

    public SellerAuthorizeException( Integer code,String message) {
        super(message);
        this.code = code;
    }

    public SellerAuthorizeException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
