package com.caffeinated.caffeinatedpersonaservice.controller;

import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.model.UserProfileDto;
import com.caffeinated.caffeinatedpersonaservice.model.UserRegistrationRequest;
import com.caffeinated.caffeinatedpersonaservice.service.IUserProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping("/users/api")
@AllArgsConstructor
@Slf4j
public class UserProfileController {
    private final IUserProfileService service;



    @GetMapping("/{email}")
    public ServiceResponse getUserDetail(@PathVariable String email) {
        logRequestInfo("getUserDetail", email);
        ServiceResponse response = service.getUserProfile(email);
        logResponseInfo("getUserDetail", response);
        return response;
    }

    @PutMapping("/{email}")
    public ServiceResponse updateUserDetail(@RequestBody UserProfileDto user, @PathVariable String email) {
        logRequestInfo("updateUserDetail", user);
        ServiceResponse response = service.updateProfile(user, email);
        logResponseInfo("updateUserDetail", response);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<String> allUser(@RequestBody UserRegistrationRequest user) {
      log.info("WOOHOOOOOOO");
      return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    private void logRequestInfo(String methodName, Object requestData) {
        log.info("Initial Request from {}: {}", methodName, kv("request", requestData));
    }

    private void logResponseInfo(String methodName, ServiceResponse responseData) {
        log.info("Final Response from {}: {}", methodName, kv("response", responseData));
    }
}
