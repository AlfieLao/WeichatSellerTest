package com.imooc.sell.service.impl;

import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dataTransformObject.OrderDto;
import com.imooc.sell.service.PushMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageImpl implements PushMessage {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public void orderStatus(OrderDto orderDto) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
        wxMpTemplateMessage.setToUser(orderDto.getBuyerOpenid());
        List<WxMpTemplateData> list = Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","1101313"),
                new WxMpTemplateData("keyword3",orderDto.getOrderId()),
                new WxMpTemplateData("keyword4",orderDto.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+orderDto.getOrderAmount()),
                new WxMpTemplateData("remark","记得好评")
        );
        wxMpTemplateMessage.setData(list);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
//            e.printStackTrace();
            log.error("【微信模板信息】发生失败,{}",e.getMessage());
            /**为什么这里不抛异常：因为订单完结后调用推送，如果推送失败，订单完成方法将会回滚*/
        }
    }
}
