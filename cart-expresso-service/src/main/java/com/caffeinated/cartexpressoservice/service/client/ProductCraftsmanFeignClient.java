package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-craftsman-service", url = "192.168.1.36:8082/api/products")
public interface ProductCraftsmanFeignClient {
    @GetMapping("/{productId}")
    public ServiceResponse getProduct(@PathVariable Integer productId) throws Exception;
}
