package com.brycehan.cloud.auth.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService{
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("admin".equals(username)) {
			return User.withUsername("admin")
					.password("123456")
					.roles("ADMIN").build();
		}else if("user".equals(username)) {
			return User.withUsername("user")
			.password("password")
			.roles("USER").build();
		}
		throw new UsernameNotFoundException("账号：" + username + "不存在");
	}
}
