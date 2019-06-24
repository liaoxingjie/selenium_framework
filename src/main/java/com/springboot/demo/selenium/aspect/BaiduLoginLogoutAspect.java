package com.springboot.demo.selenium.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BaiduLoginLogoutAspect {

    private static Logger logger = LoggerFactory.getLogger(BaiduLoginLogoutAspect.class);

    @Pointcut("execution(public * com.springboot.demo.selenium.test.*.*(..))")
    public void loginlogout(){}

    @Before("loginlogout()")
    public void baiduLogin(){
        logger.info("login success");
    }
    @After("loginlogout()")
    public void baiduLogout(){
        logger.info("logout success");
    }


}
