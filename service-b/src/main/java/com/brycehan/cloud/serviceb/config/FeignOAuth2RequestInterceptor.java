package com.brycehan.cloud.serviceb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignOAuth2RequestInterceptor implements RequestInterceptor {

	private final String AUTHORIZATION_HEADER = "Authorization";
	
	@Override
	public void apply(RequestTemplate template) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if(authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
			OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
			template.header(AUTHORIZATION_HEADER, String.format("%s %s", oAuth2AuthenticationDetails.getTokenType(), oAuth2AuthenticationDetails.getTokenValue()));
		}

	}

}
