package com.imooc.sell.Controller;

/* 卖家用户*/

import com.imooc.sell.config.ProjectUrl;
import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.dataObject.SellerInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.SellerService;
import com.imooc.sell.service.WebSocketTest;
import com.imooc.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrl projectUrl;

    @Autowired
    private WebSocketTest webSocketTest;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response, //注意
                              Map<String,Object> map){
        //openid去和数据库的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo==null)
        {
            map.put("msg",ResultEnum.LOGIN_FAILED.getMessage());
            map.put("url","/sell/seller/order/list");//登录失败应该跳转到扫描节面的
            return new ModelAndView("common/error/");
        }
        //设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.Expire;
        //这里注意 可以设置过期时间
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);

        //设置token至cookie
//        Cookie cookie=new Cookie("token",token);
//        cookie.setPath("/");
//        cookie.setMaxAge(RedisConstant.Expire);
//        response.addCookie(cookie);
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);


        //5.发生websocket消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    webSocketTest.sendMessage("有新订单:");
                }catch (Exception e){}

            }
        }).run();
        //跳转 这里跳转不能用相对地址，最后用绝对地址 用注释的地址会有两个/sell/sell
        return  new ModelAndView("redirect:"+projectUrl.getSell()+"/sell/seller/order/list");
//        return  new ModelAndView("redirect:/sell/seller/order/list");

    }

    //入参从cookie里面拿

    @GetMapping("/logout")
    public  ModelAndView logout(HttpServletRequest request,
                        HttpServletResponse response,
                        Map<String,Object> map) {
        //从cookie里面查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie ==null){
            map.put("msg",ResultEnum.LOGOUT_FAILED.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        //清除redis
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        //清除cookie
        CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
