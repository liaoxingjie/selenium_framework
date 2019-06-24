package com.springboot.demo.selenium.test;

import com.springboot.demo.selenium.page.BaiduPage;
import com.springboot.demo.selenium.web.Toools;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBaiduOne {
    private static Logger logger = LoggerFactory.getLogger(TestBaiduOne.class);

    @RequestMapping(value = "/testcase1",method = RequestMethod.GET)
    public void testcase1(){
        Toools.setProperty();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.baidu.com");
        BaiduPage baiduPage = PageFactory.initElements(driver,BaiduPage.class);

        logger.info("------baidu_search-----");

        baiduPage.sendkeys(baiduPage.kw,"1234567");
        baiduPage.click(baiduPage.su);
        logger.info("111111111111111");
        Toools.sleep(10);
        driver.quit();
    }




}
