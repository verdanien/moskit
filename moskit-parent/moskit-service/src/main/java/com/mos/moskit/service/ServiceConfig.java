package com.mos.moskit.service;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mos.moskit.domain.DomainConfig;
import com.mos.moskit.service.security.CustomGlobalMethodSecurityConfig;

@Configuration
@ImportAutoConfiguration(classes = { DomainConfig.class, CustomGlobalMethodSecurityConfig.class })
@ComponentScan(basePackages = "com.mos.moskit.service")
public class ServiceConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
