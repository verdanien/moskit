package com.mos.moskit.service;

public interface SecurityService {

	String generateSalt();

	String encryptPassword(String rawPassword);

	boolean matchPassword(String rawPassword, String encodedPassword);

}
