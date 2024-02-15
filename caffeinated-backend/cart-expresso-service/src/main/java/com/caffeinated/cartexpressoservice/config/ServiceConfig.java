package com.caffeinated.cartexpressoservice.config;

import com.caffeinated.cartexpressoservice.model.ErrorMessage;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "service-config")
@Data
public class ServiceConfig {
    private ErrorMessage externalServiceException;
    private ErrorMessage internalServerException;
    private ErrorMessage businessException;
    private Map<String, ErrorMessage> externalServiceExceptions;
}
