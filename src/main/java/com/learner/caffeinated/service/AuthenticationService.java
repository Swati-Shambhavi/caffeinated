package com.learner.caffeinated.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.repo.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	public ServiceResponse register(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("ROLE_USER");
		ServiceResponse response =ServiceResponse.builder().build();
		try {
			User userSaved = userRepository.save(user);
			response.setData(userSaved);
		}catch(Exception e) {
			Map<String, String> error=new HashMap<>();
			error.put("DB-ERROR", e.getMessage());
			response.setError(error);
		}
		return response;
	}

}
