package com.caffeinated.caffeinatedpersonaservice.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.caffeinated.caffeinatedpersonaservice.entity.Address;
import com.caffeinated.caffeinatedpersonaservice.entity.User;
import com.caffeinated.caffeinatedpersonaservice.model.AddressDto;
import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;
import com.caffeinated.caffeinatedpersonaservice.model.UserProfileDto;
import com.caffeinated.caffeinatedpersonaservice.repo.UserRepository;
import com.caffeinated.caffeinatedpersonaservice.service.IUserProfileService;
import com.caffeinated.caffeinatedpersonaservice.util.MapMeUp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import io.micrometer.common.util.StringUtils;

@Service
@AllArgsConstructor
public class UserProfileService implements IUserProfileService {
	private UserRepository userRepo;

	public ServiceResponse updateProfile(UserProfileDto userDto, String email) {
		ServiceResponse response = ServiceResponse.builder().build();
		try {
			Optional<User> __user = userRepo.findByEmail(email);
			if (__user.isEmpty()) {
				Map<String, String> error = new HashMap<>();
				error.put("600", "No user found with User email:" + email);
				response.setError(error);
				return response;
			}
			User userFromDB = __user.get();
			updateAllFields(userDto, userFromDB);
			response.setData(MapMeUp.toUserProfileDto(userRepo.save(userFromDB)));
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("500", e.getMessage());
			response.setError(error);
			return response;
		}
		return response;
	}

	private void updateAllFields(UserProfileDto userDto, User userFromDB) {
		if (StringUtils.isNotBlank(userDto.getEmail())) {
			userFromDB.setEmail(userDto.getEmail());
		}
		if (StringUtils.isNotBlank(userDto.getName())) {
			userFromDB.setName(userDto.getName());
		}
		if (StringUtils.isNotBlank(userDto.getMobileNumber())) {
			userFromDB.setMobileNumber(userDto.getMobileNumber());
		}
		if (userDto.getAddress() != null) {
			//Check if the user's address was present in db
			Address addFromDB;
			if(userFromDB.getAddress()==null){
				addFromDB = Address.builder().build();
			}else{
				addFromDB = userFromDB.getAddress();
			}
			mapAddressDetails(addFromDB,userDto.getAddress());
			userFromDB.setAddress(addFromDB);
		}
	}

	private void mapAddressDetails(Address addInDb, AddressDto _addInReq) {
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
		if(StringUtils.isNotBlank(_addInReq.getCountry())){
			addInDb.setPinCode(_addInReq.getCountry());
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
			response.setData(MapMeUp.toUserProfileDto(__user.get()));
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("500", e.getMessage());
			response.setError(error);
			return response;
		}
		return response;
	}

	/**@Override
	public ServiceResponse registerUser(UserProfileDto user) {
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
	}**/

}
