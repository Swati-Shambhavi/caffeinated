package com.caffeinated.caffeinatedpersonaservice.service;


import com.caffeinated.caffeinatedpersonaservice.model.ServiceResponse;

public interface IAdminService {

	ServiceResponse giveAdminAccess(Integer userId) throws Exception;
	ServiceResponse retrieveAdminAccess(Integer userId) throws Exception;

}
