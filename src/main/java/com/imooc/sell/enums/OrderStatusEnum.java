package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnums{
    /**订单的状态 */
    NEM(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;
    private Integer code;
    private String message;
     OrderStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public static OrderStatusEnum getString(Integer integer){
         for (OrderStatusEnum orderStatusEnum:OrderStatusEnum.values())
         {
            if (orderStatusEnum.getCode().equals(integer)){
                return  orderStatusEnum;
            }
         }
         return null;
    }
}
