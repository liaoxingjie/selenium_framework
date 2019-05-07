package com.springboot.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(int a){
        //测试jacoco对分支的覆盖
        if (a == 1){
            return "Hello a = 1";
        }else if(a == 2){
            return "Hello a = 2";
        } else {
            return "I don't know a = ?";
        }

    }
}
