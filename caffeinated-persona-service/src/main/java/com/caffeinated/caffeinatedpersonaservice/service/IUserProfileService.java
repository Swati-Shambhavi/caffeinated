package com.caffeinated.caffeinatedpersonaservice.service;

import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;

public interface IUserProfileService {
    ServiceResponse updateProfile(User user, Integer userId);
    ServiceResponse getUserProfile(String email);
    ServiceResponse registerUser(User user);
}

