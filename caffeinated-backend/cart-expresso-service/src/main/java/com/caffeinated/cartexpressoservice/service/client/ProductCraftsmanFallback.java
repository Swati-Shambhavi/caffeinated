package com.caffeinated.cartexpressoservice.service.client;

import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductCraftsmanFallback implements ProductCraftsmanFeignClient {
    @Override
    public ServiceResponse getProduct(Integer productId) throws Exception {
        return null;
    }
}
