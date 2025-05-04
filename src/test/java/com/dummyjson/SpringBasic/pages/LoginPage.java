package com.dummyjson.SpringBasic.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;

@Component
public class LoginPage {
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
