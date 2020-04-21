package com.imooc.sell.aspect;

import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.utils.CookieUtil;
import com.imooc.sell.utils.SellException;
import com.imooc.sell.utils.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/*
 * @Description:
 * @Author: AlfieLao
 * @Date: 2020/4/12 15:57
 **/
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.imooc.sell.Controller.Seller*.*(..))" +
    "&& !execution(public  * com.imooc.sell.Controller.SellerUserController.*(..))")
    public void verify(){

    }

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie ==null){
            log.warn("【登录检验】cookie中找不到token");
            throw new SellerAuthorizeException(ResultEnum.AUTHORIZE_FAILED);
        }
        //去查redis
        String openid = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(openid))
        {
            log.warn("【登录检验】Redis中找不到token");
            throw new SellerAuthorizeException(ResultEnum.AUTHORIZE_FAILED);
        }
    }

}
