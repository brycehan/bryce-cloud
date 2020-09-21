package com.brycehan.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			super.configure(http);
		}

	}
}
