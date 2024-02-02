package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class CaffeinatedPersonaFallback implements CaffeinatedPersonaFeignClient{
    @Override
    public ServiceResponse getUserDetail(String email) {
        return null;
    }
}
