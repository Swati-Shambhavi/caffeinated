package com.learner.caffeinated.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learner.caffeinated.entity.ServiceResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(SessionAuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ServiceResponse handleSessionAuthenticationException(SessionAuthenticationException ex, HttpServletResponse response)
			throws IOException {
		response.getWriter().write("Session has expired. Please log in again.");
		response.getWriter().flush();
		log.error("CONTROLLER ADVICE" + ex);
		Map<String, String> error = new HashMap<>();
		error.put("SESSION_EXP_ERR", ex.getMessage());
		return ServiceResponse.builder().error(error).build();
	}

	@ExceptionHandler(Exception.class)
	public ServiceResponse handleException(Exception e) {
		log.error("CONTROLLER ADVICE" + e);
		e.printStackTrace();
		Map<String, String> error = new HashMap<>();
		error.put("ERR", e.getMessage());
		return ServiceResponse.builder().error(error).build();

	}
}
