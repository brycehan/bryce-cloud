package com.brycehan.cloud.serviceb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brycehan.cloud.serviceb.client.ServiceAClient;

@RestController
public class BController {

	@Autowired
	private Registration registration;
	
	@Autowired
	private ServiceAClient serviceAClient;
	
	@GetMapping
	public String index() {
		StringBuffer info = new StringBuffer();
		info.append("service " + registration.getServiceId() + " (" + registration.getHost() + ":" + registration.getPort() + ") is OK.");
		info.append("<br/>");
		info.append(serviceAClient.index());
		return info.toString();
	}
}
