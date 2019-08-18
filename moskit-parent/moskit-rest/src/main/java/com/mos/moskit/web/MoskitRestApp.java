package com.mos.moskit.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.mos.moskit.service.ServiceConfig;
import com.mos.moskit.web.handler.ApiVersionRequestMappingHandler;

@SpringBootApplication
//@PropertySource(value = "classpath:/application.properties", ignoreResourceNotFound = false)
@ImportAutoConfiguration({ ServiceConfig.class, SwaggerConfig.class })
public class MoskitRestApp {

	public static void main(String[] args) {
		SpringApplication.run(MoskitRestApp.class, args);
	}

	@Bean
	WebMvcRegistrations webMvcRegistrations() {
		return new WebMvcRegistrations() {
			@Override
			public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
				return new ApiVersionRequestMappingHandler("v");
			}
		};
	}

}
