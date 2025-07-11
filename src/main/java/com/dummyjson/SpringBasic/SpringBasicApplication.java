package com.dummyjson.SpringBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringBasicApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringBasicApplication.class, args);
		Car car = applicationContext.getBean(Car.class);
		car.getCar();
	}

}
