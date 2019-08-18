package com.mos.moskit.service.impl;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mos.moskit.service.SecurityService;

@Component
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String generateSalt() {
		return BCrypt.gensalt(10, new SecureRandom());
	}

	@Override
	public String encryptPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matchPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
