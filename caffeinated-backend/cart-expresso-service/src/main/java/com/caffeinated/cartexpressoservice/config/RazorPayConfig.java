package com.caffeinated.cartexpressoservice.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RazorPayConfig {
    @Value("${razorpay-config.keyId}")
    private String apiKey;
    @Value("${razorpay-config.keySecret}")
    private String apiSecret;
    @Bean
    public RazorpayClient razorpayClient()throws RazorpayException {
        return new RazorpayClient(apiKey, apiSecret);
    }
}
