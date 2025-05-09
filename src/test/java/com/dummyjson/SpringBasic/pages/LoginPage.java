package com.dummyjson.SpringBasic.pages;

import javax.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPage {
    @Autowired
    private WebDriver webDriver;
    @PostConstruct
    public void InitLoginPage(){
        PageFactory.initElements(webDriver,this);
    }
    @FindBy(how = How.XPATH, using = "//input[@data-qa='login-email']")
    public WebElement email;
    @FindBy(how = How.XPATH, using = "//input[@placeholder='Password']")
    public WebElement password;
    @FindBy(how = How.XPATH, using = "//button[normalize-space()='Login']")
    public WebElement loginBtn;
    public void login(String userName, String password){
        System.out.println("UserName and password: "+userName+"---"+password);
    }
    public HomePage clickLogin(){
        System.out.println("Click login from login page");
        return new HomePage();
    }
}
