package com.dummyjson.SpringBasic;

import com.dummyjson.SpringBasic.pages.HomePage;
import com.dummyjson.SpringBasic.pages.LoginPage;
import com.dummyjson.SpringBasic.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootTest
class SpringBasicApplicationTests {
	@Autowired
	private MainPage mainPage;
	@Value("${app.url}")
	private String appUrl;
	@Value("chrome,firefox,edge")
	private List<String> browsers;

	@Test
	void contextLoads() {
		System.out.println(appUrl);
		browsers.forEach(System.out::println);
//		HomePage homePage = new HomePage();
//		LoginPage loginPage = new LoginPage();
//		MainPage mainPage = new MainPage(loginPage,homePage);
		mainPage.performLogin();
	}

}
