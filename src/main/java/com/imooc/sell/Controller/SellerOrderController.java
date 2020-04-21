package com.imooc.sell.Controller;

import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.SellException;
import com.lly835.bestpay.rest.type.Get;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

/* 卖家端订单*/
@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String ,Object> map){
            //page 从0开始
          Page<OrderDto> orderDtoPage = orderService.findList(PageRequest.of(page -1,size));
          map.put("orderDTOPage",orderDtoPage);
          map.put("currentPage",page);
          map.put("size",size);
          return new ModelAndView("order/list",map);
    }

    @GetMapping("/cancel")
    public  ModelAndView cancel(@RequestParam(value = "orderId")String orderId,
                                Map<String,Object> map){
        try{
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.cancel(orderDto);
            //两个都会抛sellException
        }catch (SellException e)
        {
            log.error("【卖家端取消订单】出现异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return  new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        OrderDto orderDto ;
        try {
           orderDto =  orderService.findOne(orderId);
        }catch (SellException e){
            log.error("【卖家端查询订单详情】出现异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return  new ModelAndView("common/error",map);
        }
        map.put("orderDTo",orderDto);
        return new ModelAndView("order/detail",map);
    }

    //完结订单
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){

        try {
            OrderDto orderDto = orderService.findOne(orderId);
              orderService.finish(orderDto);
        }catch (SellException e){
            log.error("【卖家端完结订单】出现异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return  new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
