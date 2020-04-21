package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//为空的字段不返回
public class ResultVO<T> {
    /**     错误代码*/
    private  Integer code;
    /**     提示信息*/
    private String msg;
    /**     数据*/
    private T data;
}
