package com.muhadev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

//TODO: remove this
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// @PropertySource("file:../../resources/application-secrets.properties")
public class SeniorPhoneApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeniorPhoneApiApplication.class, args);
	}

}
