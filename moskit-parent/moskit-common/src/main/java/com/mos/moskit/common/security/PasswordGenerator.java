package com.mos.moskit.common.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordGenerator {
	public static void main(String[] args) throws Exception {
		System.out.println(Arrays.toString(new PasswordGenerator().generate("marcin")));
		System.out.println(Arrays.toString(new PasswordGenerator().generate("marcin")));
		System.out.println(Arrays.toString(new PasswordGenerator().generate("marcin")));
	}

	public PasswordGenerator() {

	}

	public byte[] generate(String plainPassword) throws NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
		messageDigest.update(salt);

		return messageDigest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
	}
	
	
}
