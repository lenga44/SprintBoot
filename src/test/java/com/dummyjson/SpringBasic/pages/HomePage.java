package com.dummyjson.SpringBasic.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
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
