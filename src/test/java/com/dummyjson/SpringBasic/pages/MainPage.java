package com.dummyjson.SpringBasic.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainPage {
    @Autowired
    private LoginPage loginPage;
    @Autowired
    private HomePage homePage;

//    public MainPage(LoginPage loginPage, HomePage homePage) {
//        this.loginPage = loginPage;
//        this.homePage = homePage;
//    }
    public void performLogin(){
        homePage.clickLogin();
        loginPage.login("admin","1234");
        loginPage.clickLogin();
    }
}
