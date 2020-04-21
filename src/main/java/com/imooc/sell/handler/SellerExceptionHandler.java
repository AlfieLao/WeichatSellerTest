package com.imooc.sell.handler;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.config.ProjectUrl;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.utils.ResponseBankException;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.utils.SellException;
import com.imooc.sell.utils.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/*
 * @Description:拦截登录异常
 * @Author: AlfieLao
 * @Date: 2020/4/12 16:29
 **/
@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrl projectUrl;

    /**
     * @ClassName ExceptionHandler
     * @Description : 功能说明
     * @Return : 
     * @Author : AlfieLao
     * @Date : 2020/4/12 16:31
    */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
//            return new ModelAndView("redirect:"
//                    .concat(projectUrl.getWeChatOpenAuthorize())
//                    .concat("/sell/wechat/qrAuthorize")
//                    .concat("?returnUrl=")
//                    .concat(projectUrl.getSell())
//                    .concat("/sell/seller/login"));
        Map<String,Object> map =new HashMap<>();
        map.put("msg", ResultEnum.AUTHORIZE_FAILED.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/error",map);
    }

    @ExceptionHandler(value = SellException.class)
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public  ResultVO handlerResponseBankException(ResponseBankException e){
        return ResultVOUtil.error(1,e.getMessage());
    }

}
