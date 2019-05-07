package com.springboot.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        //测试
        if (true){
            return "Hello Sprint Boot";
        }else {
            return "Hello Sprint Boot";
        }

    }
}
