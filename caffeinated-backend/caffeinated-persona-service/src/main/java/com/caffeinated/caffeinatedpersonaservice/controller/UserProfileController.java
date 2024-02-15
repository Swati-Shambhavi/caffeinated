package com.caffeinated.caffeinatedpersonaservice.controller;

import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.model.UserProfileDto;
import com.caffeinated.caffeinatedpersonaservice.service.IUserProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/api")
@AllArgsConstructor
@Slf4j
public class UserProfileController {
    private IUserProfileService service;

//    @PostMapping("/register")
//    public void addNewUser(@RequestBody UserRegistrationRequest userRequest) {
//        log.info("User Registration invoked {}", kv("userRequest", userRequest));
//    }

    @GetMapping("/{email}")
    public ServiceResponse getUserDetail(@PathVariable String email) {
        log.info("User Service called for email:" + email);
        return service.getUserProfile(email);
    }

    @PutMapping("/{email}")
    public ServiceResponse updateUserDetail(@RequestBody UserProfileDto user, @PathVariable String email) {
        return service.updateProfile(user, email);
    }

//    @PostMapping("/register")
//    public ServiceResponse allUser(@RequestBody User user) {
//        return service.registerUser(user);
//    }
}
