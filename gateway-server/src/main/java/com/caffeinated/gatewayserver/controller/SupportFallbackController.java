package com.caffeinated.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SupportFallbackController {
    @RequestMapping("/contactSupportTeam")
    public Mono<String> contactSupport() {
        return Mono.just("An error occurred. Please try after some time or contact the Support Team.");
    }

}
