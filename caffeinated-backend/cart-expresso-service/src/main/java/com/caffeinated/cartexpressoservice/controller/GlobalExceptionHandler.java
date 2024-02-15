package com.caffeinated.cartexpressoservice.controller;

import com.caffeinated.cartexpressoservice.config.ServiceConfig;
import com.caffeinated.cartexpressoservice.exception.ExternalServiceException;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.ErrorData;
import com.caffeinated.cartexpressoservice.model.ErrorMessage;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {
	private final ServiceConfig serviceConfig;

	@ExceptionHandler(ExternalServiceException.class)
	public ResponseEntity<ServiceResponse> handleExtServiceException(ExternalServiceException e){
		String[] errorDescription = e.getMessage().split("-");
		Map<String, String> errorMap = new HashMap<>();
		ErrorMessage errors = serviceConfig.getExternalServiceExceptions().get(errorDescription[0]);
		errorMap.put(errors.getCode(), errors.getDescription()+". "+errorDescription[1]);
		ServiceResponse response = ServiceResponse.builder().error(ErrorData.builder().code(serviceConfig.getExternalServiceException().getCode()).message(serviceConfig.getExternalServiceException().getDescription()).errors(errorMap).build()).build();
		log.error("Final Error Response: {}",kv("response",response));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ServiceResponse> handleException(Exception e) {
		log.error("CONTROLLER ADVICE" + e);
		e.printStackTrace();
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("500001",e.getMessage());
		ServiceResponse response = ServiceResponse.builder().error(ErrorData.builder().code(serviceConfig.getInternalServerException().getCode()).message(serviceConfig.getInternalServerException().getDescription()).errors(errorMap).build()).build();
		log.error("Final Error Response: {}",kv("response",response));
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ServiceResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
		Map<String, String> errors = new HashMap<>();
		errors.put("50001",exception.getMessage());
		ServiceResponse response = ServiceResponse.builder().error(ErrorData.builder().code("5000").message("Resource Not Found").errors(errors).build()).build();
		log.error("Final Error Response: {}",kv("response",response));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
