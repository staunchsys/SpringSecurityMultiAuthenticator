package com.staunchsys.multiauthenticator.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultipleAuthenticationController {

	@GetMapping("/api/ping")
	public String getPing() {
		return "ok";
	}
}
