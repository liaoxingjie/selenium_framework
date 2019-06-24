package com.springboot.demo.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasePage {
    public static WebDriver driver;
    public static String pageTitle;
    public static String pageUrl;
    public final int timeOut = 60;
    private static Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
    public void verifyElementIsPresent(WebElement element){
        if (element.isDisplayed()) {
            logger.info("IsPresent:"+element.toString());
        } else {
            logger.info("IsNotPresent:"+element.toString());
        }
    }
    public void sendkeys(WebElement element, String s) {
        new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));
        element.clear();
        logger.info("Clean:"+element.toString());
        element.sendKeys(s);
        logger.info("SendKeys:"+s);
    }
    public void click(WebElement element) {
        new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));
        element.click();
        logger.info("Click:"+element.toString());
    }
    public void moveTo(WebElement element){
        new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));
        new Actions(driver).moveToElement(element);
    }
    public String getCurrentPageTitle(){
        pageTitle = driver.getTitle();
        logger.info("Current page title is:"+pageTitle);
        return pageTitle;
    }
    public String getCurrentPageUrl(){
        pageUrl = driver.getCurrentUrl();
        logger.info("Current page url is:"+pageUrl);
        return pageUrl;
    }

}
