package com.caffeinated.caffeinatedpersonaservice.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.caffeinated.caffeinatedpersonaservice.entity.Address;
import com.caffeinated.caffeinatedpersonaservice.entity.Cart;
import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.repo.UserRepository;
import com.caffeinated.caffeinatedpersonaservice.service.IUserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import io.micrometer.common.util.StringUtils;

@Service
@AllArgsConstructor
public class UserProfileService implements IUserProfileService {
	private UserRepository userRepo;
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	public ServiceResponse updateProfile(User user, Integer userId) {
		ServiceResponse response = ServiceResponse.builder().build();
		try {
			Optional<User> __user = userRepo.findById(userId);
			if (__user.isEmpty()) {
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
//			userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
			userFromDB.setPassword(user.getPassword());

		}
		if (user.getAddress() != null) {
			//Check if the user's address was present in db
			Address addFromDB;
			if(userFromDB.getAddress()==null){
				addFromDB = Address.builder().build();
			}else{
				addFromDB = userFromDB.getAddress();
			}
			mapAddressDetails(addFromDB,user.getAddress());
			userFromDB.setAddress(addFromDB);
		}
	}

	private void mapAddressDetails(Address addInDb, Address _addInReq) {
		if(StringUtils.isNotBlank(_addInReq.getAddress1())){
			addInDb.setAddress1(_addInReq.getAddress1());
		}
		if(StringUtils.isNotBlank(_addInReq.getAddress2())){
			addInDb.setAddress2(_addInReq.getAddress2());
		}
		if(StringUtils.isNotBlank(_addInReq.getCity())){
			addInDb.setCity(_addInReq.getCity());
		}
		if(StringUtils.isNotBlank(_addInReq.getState())){
			addInDb.setState(_addInReq.getState());
		}
		if(StringUtils.isNotBlank(_addInReq.getPinCode())){
			addInDb.setPinCode(_addInReq.getPinCode());
		}
	}

	public ServiceResponse getUserProfile(String email) {
		ServiceResponse response = ServiceResponse.builder().build();
		try {
			Optional<User> __user = userRepo.findByEmail(email);
			if (__user.isEmpty()) {
				Map<String, String> error = new HashMap<>();
				error.put("600", "No user found with email:" + email);
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

	@Override
	public ServiceResponse registerUser(User user) {
//		String encodedPassword = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encodedPassword);
		user.setRole("ROLE_USER");
		ServiceResponse response =ServiceResponse.builder().build();
		try {
			User userSaved = userRepo.save(user);
			response.setData(userSaved);
		}catch(Exception e) {
			Map<String, String> error=new HashMap<>();
			error.put("DB-ERROR", e.getMessage());
			response.setError(error);
		}
		return response;
	}

}
