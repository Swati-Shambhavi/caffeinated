package com.caffeinated.caffeinatedpersonaservice.service.impl;

import java.util.Optional;

import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.repo.UserRepository;
import com.caffeinated.caffeinatedpersonaservice.service.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import io.micrometer.common.util.StringUtils;

@Service
@AllArgsConstructor
public class AdminService implements IAdminService {
	private UserRepository userRepo;

	public ServiceResponse giveAdminAccess(Integer userId) throws Exception {
		ServiceResponse response = ServiceResponse.builder().build();
		User user = fetchUserFromDB(userId);
		if (StringUtils.isNotBlank(user.getRole()) && user.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
			response.setData("The User is already an ADMIN");
		} else if (StringUtils.isNotBlank(user.getRole()) && user.getRole().equalsIgnoreCase("ROLE_USER")) {
			user.setRole("ROLE_ADMIN");
			User updatedUser = userRepo.save(user);
			response.setData(updatedUser);
		}
		return response;
	}

	public ServiceResponse retrieveAdminAccess(Integer userId) throws Exception {
		ServiceResponse response = ServiceResponse.builder().build();
		User user = fetchUserFromDB(userId);
		if (StringUtils.isNotBlank(user.getRole()) && user.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
			user.setRole("ROLE_USER");
			User updatedUser = userRepo.save(user);
			response.setData(updatedUser);
		} else if (StringUtils.isNotBlank(user.getRole()) && user.getRole().equalsIgnoreCase("ROLE_USER")) {
			response.setData("The User is not an ADMIN");
		}
		return response;
	}

	private User fetchUserFromDB(Integer userId) throws Exception {
		Optional<User> userOptional = userRepo.findById(userId);
		if (userOptional.isEmpty()) {
			throw new Exception("User not found for id:" + userId);
		}
		return userOptional.get();
	}

}
