package com.caffeinated.gatewayserver.controller;

import com.caffeinated.gatewayserver.dto.Error;
import com.caffeinated.gatewayserver.dto.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SupportFallbackController {
    @RequestMapping("/contactSupportTeam")
    public Mono<ResponseEntity<ServiceResponse>> contactSupportFallback() {
        String fallbackMessage = "An error occurred. Please try after some time or contact the Support Team.";
        ServiceResponse response = ServiceResponse.builder().data(null).error(Error.builder().code("500")
                .message("An error occurred. Please try after some time or contact the Support Team.").build()).build();
        return Mono.just(ResponseEntity.status(500).body(response));
    }

}
