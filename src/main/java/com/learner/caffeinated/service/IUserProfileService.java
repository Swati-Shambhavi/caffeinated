package com.learner.caffeinated.service;

import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;

public interface IUserProfileService {
    ServiceResponse updateProfile(User user, Integer userId);
    ServiceResponse getUserProfile(Integer userId);
    }
