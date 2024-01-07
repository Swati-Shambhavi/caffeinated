package com.caffeinated.caffeinatedpersonaservice.controller;

import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.service.IUserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserProfileController {
    private IUserProfileService service;

    @GetMapping("/{email}")
    public ServiceResponse getUserDetail(@PathVariable String email) {
        return service.getUserProfile(email);
    }

    @PutMapping("/{userId}")
    public ServiceResponse updateUserDetail(@RequestBody User user, @PathVariable Integer userId) {
        return service.updateProfile(user, userId);
    }
}
