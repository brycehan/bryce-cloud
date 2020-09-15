package com.brycehan.cloud.auth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping(value = "current")
	public Principal getUser(Principal principal) {
		return principal;
	}
}
