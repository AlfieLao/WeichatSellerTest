package com.imooc.sell.Controller;

import com.imooc.sell.config.ProjectUrl;
import com.imooc.sell.config.WeChatMpConfig;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.utils.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/** 返回Json，不会跳转的，不会重定向到/userInfo*/
//@RestController
@Controller
@Slf4j
@RequestMapping("/wechat")
public class  WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private  WxMpService wxOpenService;

    @Autowired
    private ProjectUrl projectUrl;

    /**
     * 注意url 各个位置的大小写
     * 访问http://alfieapp.natapp1.cc/sell/wechat/authorize?returnUrl=http;//www.baidu.com
     *alfieapp.natapp1.cc/sell/pay/create?orderid=oTgZpwdxPNubsU9SiXkQ8jv1KzeQ&returnUrl=sell.com
     **/
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl) throws UnsupportedEncodingException {
        //  带着code/url参数跳转去 下一个接口
        String url =projectUrl.getWeChatMpAuthorize()+ "/sell/wechat/userInfo";
        /**
         *下面这个方法是第一步，获取微信授权的code  传入用户授权完成后的重定向链接
         * "GB2312" "utf-8"  这个方法第三个参数是state，传什么返回什么
         * */
        String redirectUrl  = wxMpService.oauth2buildAuthorizationUrl
                (url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl,"utf-8"));
        log.info("【微信网页授权】：获取code,result ={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                            @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken auth2AccessToken;
        WxMpUser userInfo;
        try {
            auth2AccessToken = wxMpService.oauth2getAccessToken(code);
            userInfo = wxMpService.oauth2getUserInfo(auth2AccessToken,"zh_CN");
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("【微信网页授权】{}", e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openid = auth2AccessToken.getOpenId();
        log.info("【微信用户信息：城市】{}",userInfo.getCity());
        log.info("【微信用户信息：openid】{}",openid);
        return "redirect:" +returnUrl +"?openid="+openid;
    }

    //公众平台获取code去下一个接口获取openid
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl) throws UnsupportedEncodingException {
            String url=projectUrl.weChatOpenAuthorize+"/sell/wechat/qrUserInfo";
            String redirectUrl=wxOpenService.buildQrConnectUrl(url,
                    WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
            return "redirect:"+redirectUrl;
    }
    //转发通过code获取openid
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code")String code,
                           @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken auth2AccessToken;
        WxMpUser userInfo;
        try {
            auth2AccessToken = wxOpenService.oauth2getAccessToken(code);
            userInfo = wxOpenService.oauth2getUserInfo(auth2AccessToken,"zh_CN");
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("【微信网页授权】{}", e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openid = auth2AccessToken.getOpenId();
        log.info("【微信用户信息：城市】{}",userInfo.getCity());
        log.info("【微信用户信息：openid】{}",openid);
        return "redirect:" +returnUrl +"?openid="+openid;
    }
}
