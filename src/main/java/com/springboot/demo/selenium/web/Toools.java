package com.springboot.demo.selenium.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tools")
public class Toools {

    @Autowired
    WebApplicationContext applicationContext;

    public static void setProperty(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Administrator\\IdeaProjects\\demo\\src\\main\\java\\com\\springboot\\demo\\selenium\\driver\\chromedriver75.exe");
    }

    @RequestMapping(value = "/getAllTestCase", method = RequestMethod.GET)
    private Object getAllTestCase() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();

            Map<String, String> map1 = new HashMap<String, String>();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }

            String className = method.getMethod().getDeclaringClass().getName();
            //指定过滤
            if (className.contains("org.springframework.boot.autoconfigure.web.servlet.") ||
                    className.contains("com.springboot.demo.controller.AreaController") ||
                    className.contains("com.springboot.demo.selenium.web.Toools")) {
                continue;
            }
            map1.put("className", method.getMethod().getDeclaringClass().getName());
            map1.put("method", method.getMethod().getName());
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }
            list.add(map1);
        }
        //JSONArray jsonArray = JSONArray.fromObject(list);
        return list;
    }

    public static void sleep(int time){
        try {
            Thread.sleep(1000*time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
