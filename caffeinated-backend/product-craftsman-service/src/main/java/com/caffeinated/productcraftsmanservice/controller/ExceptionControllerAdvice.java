package com.caffeinated.productcraftsmanservice.controller;

import com.caffeinated.productcraftsmanservice.dto.Error;
import com.caffeinated.productcraftsmanservice.exception.ResourceNotFoundException;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ServiceResponse> resourceNotFoundHandler(ResourceNotFoundException error) {
        Map<String, String> errors = new HashMap<>();
        errors.put("50001",error.getMessage());
        ServiceResponse response = ServiceResponse.builder().error(Error.builder().code("5000").message("Resource Not Found").errors(errors).build()).build();
        log.error("Final Error Response: {}",kv("response",response));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ServiceResponse response = buildValidationErrorDetails(bindingResult);
        log.error("Final Error Response: {}",kv("response",response));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ServiceResponse buildValidationErrorDetails(BindingResult bindingResult) {
        Map<String, String> validationErrors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage()));
        return ServiceResponse.builder()
                .error(Error.builder().code("4000").message("Validation error").errors(validationErrors).build())
                .build();
    }
}
