package com.caffeinated.cartexpressoservice.service.externalservice;


import com.caffeinated.cartexpressoservice.exception.ExternalServiceException;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.ProductDto;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.service.client.ProductCraftsmanFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProductCraftsmanExternalService {

    private final ProductCraftsmanFeignClient productCraftsmanFeignClient;

    @Retry(name = "productCraftsmanRetry", fallbackMethod = "fallbackMethod")
    public ProductDto getProduct(Integer productId) throws Exception {
        log.info("Calling Product External Service");
        ServiceResponse productDetail = productCraftsmanFeignClient.getProduct(productId);
        if (productDetail == null) {
            throw new ExternalServiceException("Error calling the Products External Service");
        } else if (productDetail.getData() == null) {
            throw new ResourceNotFoundException("Product", "productId", productId.toString());
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(productDetail.getData(), ProductDto.class);
        }
    }

    public ServiceResponse fallbackMethod(Integer productId, Throwable throwable) {
        log.info("Retried calling the Product external Service");
        return null;
    }
}
