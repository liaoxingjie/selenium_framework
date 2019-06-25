package com.springboot.demo.selenium.aspect;

import com.springboot.demo.selenium.web.Toools;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
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
        Toools.initChromeDriver("http://www.baidu.com");
        logger.info("login success");
    }
//    @Around(value="loginlogout()")
//    public Object aroundAdvisor(ProceedingJoinPoint point) throws Throwable{
//        System.out.println("before method");
//        MethodSignature signature = (MethodSignature)point.getSignature();
//
//        System.out.println(signature.getDeclaringTypeName()+"."+signature.getName());
//        //before
//        Object target = point.proceed();
//        //after
//        System.out.println("after method");
//        return target;
//    }
    @After("loginlogout()")
    public void baiduLogout(){
        logger.info("logout success");
        Toools.sleep(10);
        Toools.driverMap.get("1").quit();
    }


}
