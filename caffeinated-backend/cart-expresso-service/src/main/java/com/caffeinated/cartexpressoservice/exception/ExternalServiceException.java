package com.caffeinated.cartexpressoservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalServiceException extends RuntimeException{
    public ExternalServiceException(String s) {
        super(s);
    }
}
