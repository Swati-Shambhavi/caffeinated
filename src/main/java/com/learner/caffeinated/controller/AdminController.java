package com.learner.caffeinated.controller;

import com.learner.caffeinated.service.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.service.impl.AdminService;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	private IAdminService service;
	
	@GetMapping("/giveAdminAccess/{userId}")
	public ServiceResponse giveAdminRole(@PathVariable Integer userId) throws Exception {
		return service.giveAdminAccess(userId);
	}
	
	@GetMapping("/retrieveAdminAccess/{userId}")
	public ServiceResponse retrieveAdminRole(@PathVariable Integer userId) throws Exception {
		return service.retrieveAdminAccess(userId);
	}
}