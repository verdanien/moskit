package com.mos.moskit.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mos.moskit.domain.DomainConfig;

@SpringBootApplication
//@PropertySource(value = "classpath:/application.properties", ignoreResourceNotFound = false)
@ImportAutoConfiguration({ DomainConfig.class, SwaggerConfig.class })
public class MoskitRestApp {

	public static void main(String[] args) {
		SpringApplication.run(MoskitRestApp.class, args);
	}

}
