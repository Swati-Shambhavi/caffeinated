package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "caffeinated-persona-service", fallback = CaffeinatedPersonaFallback.class)
public interface CaffeinatedPersonaFeignClient {
    @Retry(name="caffeinatedPersonaRetry", fallbackMethod = "fallbackMethod")
    @GetMapping("/users/api/{email}")
    ServiceResponse getUserDetail(@PathVariable String email);

    default ServiceResponse fallbackMethod(String email, Throwable throwable) {
        return null;
    }
}