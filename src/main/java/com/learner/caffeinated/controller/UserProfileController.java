package com.learner.caffeinated.controller;

import com.learner.caffeinated.service.IUserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.service.impl.UserProfileService;

@RestController
@RequestMapping("/private/user")
@AllArgsConstructor
public class UserProfileController {
	private IUserProfileService service;
	
	@GetMapping("/{userId}")
	private ServiceResponse getUserProfile(@PathVariable Integer userId) {
		return service.getUserProfile(userId);
	}
	
	@PutMapping("/{userId}")
	private ServiceResponse updateUserProfile(@RequestBody User user, @PathVariable Integer userId) {
		return service.updateProfile(user,userId);
	}
}
