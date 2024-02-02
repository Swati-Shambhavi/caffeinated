package com.caffeinated.cartexpressoservice.controller;

import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.ErrorResponse;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ServiceResponse handleException(Exception e) {
		log.error("CONTROLLER ADVICE" + e);
		e.printStackTrace();
		Map<String, String> error = new HashMap<>();
		error.put("ERR", e.getMessage());
		return ServiceResponse.builder().error(error).build();

	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
																		 WebRequest webRequest) {
		ErrorResponse errorResponseDTO = new ErrorResponse(
				webRequest.getDescription(false),
				HttpStatus.NOT_FOUND,
				exception.getMessage(),
				LocalDateTime.now()
		);
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}
}
