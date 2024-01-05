package com.learner.caffeinated.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.repo.UserRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class UserProfileService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ServiceResponse updateProfile(User user, Integer userId) {
		ServiceResponse response = ServiceResponse.builder().build();
		try {
			Optional<User> __user = userRepo.findById(userId);
			if (!__user.isPresent()) {
				Map<String, String> error = new HashMap<>();
				error.put("600", "No user found with User id:" + userId);
				response.setError(error);
				return response;
			}
			User userFromDB = __user.get();
			updateAllFields(user, userFromDB);
			response.setData(userRepo.save(userFromDB));
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("500", e.getMessage());
			response.setError(error);
			return response;
		}

		return response;
	}

	private void updateAllFields(User user, User userFromDB) {
		if (StringUtils.isNotBlank(user.getEmail())) {
			userFromDB.setEmail(user.getEmail());
		}
		if (StringUtils.isNotBlank(user.getName())) {
			userFromDB.setName(user.getName());
		}
		if (StringUtils.isNotBlank(user.getMobileNumber())) {
			userFromDB.setMobileNumber(user.getMobileNumber());
		}
		if (StringUtils.isNotBlank(user.getPassword())) {
			userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		if (user.getAddress() != null) {
			userFromDB.setAddress(user.getAddress());
		}
	}

	public ServiceResponse getUserProfile(Integer userId) {
		ServiceResponse response = ServiceResponse.builder().build();
		try {
			Optional<User> __user = userRepo.findById(userId);
			if (!__user.isPresent()) {
				Map<String, String> error = new HashMap<>();
				error.put("600", "No user found with User id:" + userId);
				response.setError(error);
				return response;
			}
			response.setData(__user.get());
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("500", e.getMessage());
			response.setError(error);
			return response;
		}

		return response;
	}

}
