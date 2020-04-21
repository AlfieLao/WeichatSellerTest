package com.imooc.sell.utils;

import com.imooc.sell.enums.ResultEnum;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException{ //RuntimeException有msg属性

    private Integer code;

    public SellException( Integer code,String message) {
        super(message);
        this.code = code;
    }

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
