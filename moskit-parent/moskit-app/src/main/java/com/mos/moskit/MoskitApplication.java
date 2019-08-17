package com.mos.moskit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mos.moskit.domain.DomainConfig;

@SpringBootApplication
@ImportAutoConfiguration({ DomainConfig.class })
public class MoskitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoskitApplication.class, args);
	}

}
