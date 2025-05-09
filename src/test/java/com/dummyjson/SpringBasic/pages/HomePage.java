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
public class HomePage {
    @Autowired
    private WebDriver webDriver;
    @PostConstruct
    public void InitHomePage(){
        PageFactory.initElements(webDriver,this);
    }
    @FindBy(how = How.XPATH, using = "//a[@href='/login']")
    public WebElement login;

    @FindBy(how = How.XPATH, using = "//a[@href='/products']")
    public WebElement products;

    public LoginPage clickLogin(){
        //login.click();
        System.out.println("Click the home page login");
        return new LoginPage();
    }
    public void clickProducts(){
        products.click();
    }
}
