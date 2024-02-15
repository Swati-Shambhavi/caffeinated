package com.caffeinated.caffeinatedpersonaservice.service;

import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.model.UserProfileDto;

public interface IUserProfileService {
    ServiceResponse updateProfile(UserProfileDto user, String email);
    ServiceResponse getUserProfile(String email);
//    ServiceResponse registerUser(UserProfileDto user);
}

