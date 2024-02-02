package com.caffeinated.caffeinatedpersonaservice.controller;

import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.service.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/api")
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