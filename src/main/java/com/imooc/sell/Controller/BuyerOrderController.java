package com.imooc.sell.Controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderFormToOrderDTOConverter;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.utils.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**创建订单 */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建表单】参数不正确：,orderform={}", orderForm);
            throw  new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto = OrderFormToOrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建表单】购物车信息不能为空");
            throw  new SellException(ResultEnum.Cart_Empty);
        }
        OrderDto result = orderService.create(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVOUtil.success(map);
    }

    /** 订单列表*/
    @GetMapping("/list")
    public ResultVO<List<OrderDto>> list(@RequestParam("openid")String openid,
                                         @RequestParam(value = "page",defaultValue = "0")Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询单据列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //orderDtoPage.getTotalElements()总数
        //转化 Date ->long
        Page<OrderDto> orderDtoPage= orderService.findList(openid, PageRequest.of(page, size));
        return ResultVOUtil.success(orderDtoPage.getContent());

    }

    /**订单详情*/
    @GetMapping("/detail")
    public ResultVO<OrderDto> details(@RequestParam("openid")String openid,
                                        @RequestParam(value = "orderId")String orderId){
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            log.error("【查询单据明细】:参数为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"参数为空");
        }
        OrderDto result = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(result);
    }

    /** 取消订单*/
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,
                           @RequestParam(value = "orderId")String orderId) {
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            log.error("【查询单据明细】:参数为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), "参数为空");
        }
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
