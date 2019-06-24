package com.springboot.demo.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaiduPage extends BasePage {

    private static Logger logger = LoggerFactory.getLogger(BaiduPage.class);

    @FindBy(id="kw")
    public WebElement kw;

    @FindBy(id="su")
    public WebElement su;

    @FindBy(xpath = "//*[@id=\"u1\"]/a[7]")
    public WebElement login;

    @FindBy(id = "TANGRAM__PSP_10__footerULoginBtn")
    public WebElement usernamelogin;

    @FindBy(id = "TANGRAM__PSP_10__footerQrcodeBtn")
    public WebElement qrcodelogin;

    @FindBy(id = "TANGRAM__PSP_10__userName")
    public WebElement usernameinput;

    @FindBy(id = "TANGRAM__PSP_10__password")
    public WebElement passwordinput;

    @FindBy(id = "TANGRAM__PSP_10__submit")
    public WebElement clicklogin;

//    @FindBy(className = "user-name")
//    public WebElement user;
//
//    @FindBy(xpath = "//*[@id=\"s_user_name_menu\"]/div/a[4]")
//    public WebElement logout;

    public BaiduPage(WebDriver driver){super(driver);}


}
