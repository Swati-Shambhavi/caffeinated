package com.caffeinated.cartexpressoservice.service.externalservice;

import com.caffeinated.cartexpressoservice.exception.ExternalServiceException;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.externalservice.ProductResponse;
import com.caffeinated.cartexpressoservice.model.externalservice.ProductResponseData;
import com.caffeinated.cartexpressoservice.service.client.ProductCraftsmanFeignClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Slf4j
@AllArgsConstructor
public class ProductCraftsmanExternalService {

    private final ProductCraftsmanFeignClient productCraftsmanFeignClient;

    @Retry(name = "productCraftsmanRetry", fallbackMethod = "fallbackMethod")
    public ProductResponseData getProduct(Integer productId) throws Exception {
        log.info("Calling Product External Service");
        ResponseEntity<ProductResponse> productResponseEntity = null;
        ProductResponse productReponse;
            productResponseEntity = productCraftsmanFeignClient.getProduct(productId);
            log.info("Raw product response {} ", kv("productResponse", productResponseEntity));
            if(productResponseEntity!=null){
                productReponse = productResponseEntity.getBody();
            }else{
                throw new ExternalServiceException("Error calling the Products External Service");
            }
            if(productReponse.getError()!=null){
                throw new ExternalServiceException("productCraftsmanService-"+productReponse.getError().getMessage());
            }
            return productReponse.getData();
        }

    public ResponseEntity<ProductResponse>  fallbackMethod(Integer productId, Throwable throwable)throws Exception {
        log.info("Retried calling the Product external Service");
        return null;
    }
}
