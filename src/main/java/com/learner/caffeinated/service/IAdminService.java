package com.learner.caffeinated.service;

import com.learner.caffeinated.entity.ServiceResponse;

public interface IAdminService {

	ServiceResponse giveAdminAccess(Integer userId) throws Exception;
	ServiceResponse retrieveAdminAccess(Integer userId) throws Exception;

}
