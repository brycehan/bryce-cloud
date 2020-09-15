package com.brycehan.cloud.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	public AuthenticationManager authenticationManager;
	
	@Autowired
	public UserDetailsService userDetailsService;
	
	@Bean
	public TokenStore tokenStore(){
		return new JdbcTokenStore(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.userDetailsService(userDetailsService);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory()
		.withClient("auth")
		.secret(passwordEncoder.encode("123456"))
		.autoApprove(true)
		.redirectUris("http://localhost:8060/")
		.scopes("user")
		.authorizedGrantTypes("authorization_code")
		.accessTokenValiditySeconds(2 * 3600) // 2 hours
		.and()
		.withClient("client")
		.secret(passwordEncoder.encode("secret"))
		.authorizedGrantTypes("password", "refresh_token")
		.scopes("read", "write")
		.accessTokenValiditySeconds(3600) // 1 hour
		.refreshTokenValiditySeconds(30 * 24 * 3600) // 30 days
		.and()
		.withClient("service-a")
		.secret("password")
		.authorizedGrantTypes("client_credentials", "refresh_token")
		.scopes("server")
		.and()
		.withClient("service-b")
		.secret("password")
		.authorizedGrantTypes("client_credentials", "refresh_token")
		.scopes("server");
	}

}
