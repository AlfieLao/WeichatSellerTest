package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    /**储存返回异常错误代码 */
    SUCCESS(0,"成功"),
    PARAM_ERROR(1,"参数不正确"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_ID_NOT_EXIST(11,"商品id为空"),
    PRODUCT_STOCK_ERROR(13,"商品库存不正确"),
    PRODUCT_STATUS_ERROR(14,"商品状态不正确"),

    ORDER_NOT_EXIST(31,"单据不存在"),
    ORDER_DETAIL_NOT_EXIST(32,"单据细明不存在"),
    ORDER_STATUS_ERROR(33,"订单状态不正确"),
    ORDER_UPDATE_FAIL(34,"更新单据失败"),
    ORDER_DETAIL_EMPTY(35,"单据细明为空"),
    ORDER_PAY_STATUS_ERROR(36,"订单支付状态不正确"),
    ORDER_OWNER_ERROR(37,"该订单不属于当前用户"),
    Cart_Empty(51,"购物车为空"),

    WECHAT_MP_ERROR(60,"微信公众账号方面错误"),
    WECHAT_NOTIFY_MONEY_VERIFY_ERROR(61,"微信异步通知校验不通过"),
    ORDER_CANCEL_SUCCESS(100,"订单取消成功"),
    ORDER_FINISH_SUCCESS(101,"订单完结成功"),

    LOGIN_FAILED(120,"登录失败，登录信息不正确"),
    LOGOUT_FAILED(121,"登出失败,cookie为空"),
    LOGOUT_SUCCESS(122,"登出成功"),
    AUTHORIZE_FAILED(123,"没有权限"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code,String message){
        this.code = code;
        this.message =message;
    }
}
