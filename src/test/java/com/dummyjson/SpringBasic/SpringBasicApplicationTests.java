package com.dummyjson.SpringBasic;

import com.dummyjson.SpringBasic.pages.HomePage;
import com.dummyjson.SpringBasic.pages.LoginPage;
import com.dummyjson.SpringBasic.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("qa")
class SpringBasicApplicationTests {
	@Autowired
	private MainPage mainPage;
	@Value("${app.url}")
	private String appUrl;
	@Value("chrome,firefox,edge")
	private List<String> browsers;
	@Autowired
	private WebDriver webDriver;

	@Test
	void contextLoads() {
		webDriver.navigate().to(appUrl);
//		System.out.println(appUrl);
//		browsers.forEach(System.out::println);
//		HomePage homePage = new HomePage();
//		LoginPage loginPage = new LoginPage();
//		MainPage mainPage = new MainPage(loginPage,homePage);
		mainPage.performLogin();
	}

}
