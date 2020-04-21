package com.imooc.sell.Controller;


import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.utils.SellException;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author AlfieLao
 * @Description //支付
 * @Date 22:35 2019/11/25 0025
 * @verson :
 **/
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private PayService payService;
    //用这个接口，先转发到廖师兄的接口转发
    //#http://proxy.springboot.cn/pay?openid=oTgZpwdxPNubsU9SiXkQ8jv1KzeQ&orderId=156692370306288448&returnUrl=http://www.imooc.com
    //alfieapp.natapp1.cc/sell/pay/create?orderId=156692370306288449&returnUrl=sell.com
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                                Map<String,Object> map){
        //1.查询订单
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2.发起支付
        PayResponse payResponse = payService.create(orderDto);

//        map.put("orderId","11111111");
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);
    }

    /** 异步通知
     * @return*/
    @PostMapping("/notify")
    public ModelAndView notify(@RequestParam String notifyData){
         //这里微信返回xml的字符串 带有支付流水号
        payService.notify(notifyData);
        return new ModelAndView("pay/success");
    }

}
