package com.caffeinated.caffeinatedpersonaservice.controller;

import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
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

    @GetMapping("/{email}")
    public ServiceResponse getUserDetail(@PathVariable String email) {
        log.info("User Service called for email:"+email);
        return service.getUserProfile(email);
    }

    @PutMapping("/{userId}")
    public ServiceResponse updateUserDetail(@RequestBody User user, @PathVariable Integer userId) {
        return service.updateProfile(user, userId);
    }

    @PostMapping("/register")
    public ServiceResponse allUser(@RequestBody User user){
        return service.registerUser(user);
    }
}
