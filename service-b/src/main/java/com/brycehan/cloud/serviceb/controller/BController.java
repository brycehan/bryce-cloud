package com.brycehan.cloud.serviceb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brycehan.cloud.serviceb.client.ServiceAClient;

@RefreshScope
@RestController
public class BController {

	@Autowired
	private Registration registration;
	
	@Autowired
	private ServiceAClient serviceAClient;
	
	@Autowired
	private Environment environment;
	
	@Value("${server.port:unknown}")
	private String port;
	
	@GetMapping
	public String index() {
		StringBuffer info = new StringBuffer();
		info.append("service " + registration.getServiceId() + " (" + registration.getHost() + ":" + registration.getPort() + ") is OK.");
		info.append(" " + environment.getProperty("server.port") + " " + port);
		info.append("<br/>");
		info.append(serviceAClient.index());
		return info.toString();
	}
}
