package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.externalservice.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-craftsman-service", fallback = ProductCraftsmanFallback.class)
public interface ProductCraftsmanFeignClient {
    @GetMapping("/products/api/{productId}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable Integer productId) throws Exception;

}
