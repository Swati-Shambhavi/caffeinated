package com.caffeinated.cartexpressoservice.service.externalservice;

import com.caffeinated.cartexpressoservice.exception.ExternalServiceException;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.UserDto;
import com.caffeinated.cartexpressoservice.service.client.CaffeinatedPersonaFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CaffeinatedPersonaExternalService {
    private CaffeinatedPersonaFeignClient caffeinatedPersonaFeignClient;

    @Retry(name = "userServiceRetry")
    public UserDto getUser(String email)
    {
        log.info("Calling User External Service");
        ServiceResponse userDetail = caffeinatedPersonaFeignClient.getUserDetail(email);
        UserDto userDto;
        if(userDetail==null){
            throw new ExternalServiceException("Error calling the User External Service");
        }
        else if(userDetail.getData()==null){
            throw new ResourceNotFoundException("User","email",email);
        }else{
            ObjectMapper objectMapper = new ObjectMapper();
            userDto = objectMapper.convertValue(userDetail.getData(), UserDto.class);
            return userDto;
        }
    }
}
