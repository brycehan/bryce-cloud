package com.brycehan.cloud.servicea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class AController {

	@Autowired
	private Registration registration;
	
	@Value("${name:unknown}")
	private String name;
	
	@GetMapping(value = "/")
	public Object index() {
		return "service " + registration.getServiceId() + " ( " + registration.getHost() + ":" + registration.getPort() + " ) is OK. ===> name: " + name;
	}
}
