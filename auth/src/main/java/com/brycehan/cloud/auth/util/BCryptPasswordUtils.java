package com.brycehan.cloud.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordUtils {

	public static String encode(String rawPassword) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = bCryptPasswordEncoder.encode(rawPassword);
		return password;
	}
	
	public static void main(String[] args) {
		String rawPassword = "secret";
		System.out.println("明文密码：" + rawPassword);
		System.out.println("密文密码：" + encode(rawPassword));
	}
}
