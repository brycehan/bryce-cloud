package com.brycehan.cloud.servicea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {

	@Autowired
	private Registration registration;
	
	@GetMapping(value = "/")
	public Object index() {
		return "service " + registration.getServiceId() + " ( " + registration.getHost() + ":" + registration.getPort() + " ) is OK.";
	}
}
