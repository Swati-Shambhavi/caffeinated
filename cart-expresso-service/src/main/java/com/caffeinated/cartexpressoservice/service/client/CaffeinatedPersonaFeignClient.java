package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "caffeinated-persona-service", url = "192.168.1.36:8081/api/users")
public interface CaffeinatedPersonaFeignClient {
    @GetMapping("/{email}")
    public ServiceResponse getUserDetail(@PathVariable String email);
}