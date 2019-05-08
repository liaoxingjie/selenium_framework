package com.springboot.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloTest {

    @Test
    public void hello() {
//       测试jacoco的maven插件是否可以正确获取源代码高亮，结论是可以
        Hello h = new Hello();
        h.hello(1);
    }
}