package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-craftsman-service", fallback = ProductCraftsmanFallback.class)
public interface ProductCraftsmanFeignClient {
    @GetMapping("/api/products/{productId}")
    public ServiceResponse getProduct(@PathVariable Integer productId) throws Exception;
}
