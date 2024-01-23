package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "caffeinated-persona-service")
public interface CaffeinatedPersonaFeignClient {
    @GetMapping("/api/users/{email}")
    ServiceResponse getUserDetail(@PathVariable String email);
}