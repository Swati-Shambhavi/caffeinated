package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-craftsman-service")
public interface ProductCraftsmanFeignClient {
    @Retry(name="productCraftsmanRetry", fallbackMethod = "fallbackMethod")
    @GetMapping("/products/api/{productId}")
    ServiceResponse getProduct(@PathVariable Integer productId) throws Exception;

    default ServiceResponse fallbackMethod(Integer productId, Throwable throwable) {
        return null;
    }
}
