package com.learner.caffeinated.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/public/auth")
@Slf4j
public class AuthenticationController {
	@Autowired
	private AuthenticationService authService;

	@PostMapping("/register")
	public ServiceResponse register(@RequestBody User user) {
		return authService.register(user);
	}
	
	 @GetMapping("/session-expired")
	    public ServiceResponse handleSessionExpired() {
		 log.info("From controller");
		 Map<String, String> error = new HashMap<>();
			error.put("SESSION_EXP_ERR", "Session has expired. Please log in again.");
			return ServiceResponse.builder().error(error).build();
	    }
}
