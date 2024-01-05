package com.learner.caffeinated.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learner.caffeinated.entity.Contact;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.repo.ContactRepo;
import com.learner.caffeinated.repo.UserRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class AdminService {
	@Autowired
	private ContactRepo contactRepo;
	@Autowired
	private UserRepository userRepo;

	public ServiceResponse getAllContactDetails() {
		ServiceResponse response = ServiceResponse.builder().build();
		List<Contact> allContactDetails = contactRepo.findAll();
		response.setData(allContactDetails);
		return response;
	}

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
		if (!userOptional.isPresent()) {
			throw new Exception("User not found for id:" + userId);
		}
		return userOptional.get();
	}

}
