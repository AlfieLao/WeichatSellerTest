package com.imooc.sell;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;  /**这个test不要引用错*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
//        logger.debug("debug...");
//        logger.info("info...");
//        logger.error("error...");
        String name = "liu";
        String psw = "123";
        log.info("name:{} ,psw:{}",name,psw   ); //这个写法需要下载lombok插件
        log.debug("debug...");
        log.info("info...");
        log.warn("warn...");
        log.error("error...");

    }
}
