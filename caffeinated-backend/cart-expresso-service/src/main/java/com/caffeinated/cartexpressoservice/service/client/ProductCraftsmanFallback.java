package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.externalservice.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductCraftsmanFallback implements ProductCraftsmanFeignClient {
    @Override
    public ResponseEntity<ProductResponse> getProduct(Integer productId) throws Exception {
        return null;
    }
}
