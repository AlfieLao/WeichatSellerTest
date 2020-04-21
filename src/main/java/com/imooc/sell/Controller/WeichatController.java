package com.imooc.sell.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author AlfieLao
 * @Description //TODO
 * @Date 23:37 2019/10/21 0021
 * @verson :
 **/
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeichatController {

    /**
      * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf61a3bbbae5f5c12&redirect_uri=http://alfieapp.natapp1.cc/sell/weixin/auth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
     * 访问这个IP，带着code参数跳转到下面的方法
      * */
    @GetMapping("/auth")
    public void auth(@RequestParam("code")String code){
        log.info("进入auth方法");
        log.info("code={}",code);
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxf61a3bbbae5f5c12&secret=55881296ca554d1a26ff00a6f2d9bcc6&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String reponse = restTemplate.getForObject(url, String.class);
        log.info(reponse);

        String useInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=26_IR_CnWjkYI-2Ro4WAzQsIMylbW04QcVidp2ASGUdAWHbtUfiXapDzV_Fhm7R2tVr9hakQMlUedfvgarKoSMo36I-5KmKEpKb0nQfuLf4cxM&openid=ovlHgwnKcSoS85I0zmecGnC0munM&lang=zh_CN";
        reponse = restTemplate.getForObject(useInfoUrl, String.class);
        log.info(reponse);
    }
}
