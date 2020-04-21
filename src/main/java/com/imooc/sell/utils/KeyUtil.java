package com.imooc.sell.utils;

import java.util.Random;

/** 生成自增ID*/
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     *
     * */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(90000)+10000;
        return  System.currentTimeMillis() + String.valueOf(number);
    }
}
