package com.caffeinated.cartexpressoservice.controller;

import com.caffeinated.cartexpressoservice.model.order.CreateOrderRequest;
import com.caffeinated.cartexpressoservice.model.order.OrderConfirmationRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.order.UserDto;
import com.caffeinated.cartexpressoservice.service.IOrderService;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@Slf4j
@RequestMapping("/orders/api")
public class OrderController {

    @Autowired
    private IOrderService service;

    @PostMapping(value = "/create-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> createOrder(@RequestBody CreateOrderRequest orderRequest) throws RazorpayException {
        logRequestInfo("createOrder", orderRequest);
        ServiceResponse response = service.createOrder(orderRequest);
        logResponseInfo("createOrder", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/confirm-order/{userId}")
    public ResponseEntity<ServiceResponse> handleOrderConfirmation(@RequestBody OrderConfirmationRequest orderRequest, @PathVariable Integer userId) {
        logRequestInfo("handleOrderConfirmation", orderRequest);
        ServiceResponse response = service.orderConfirmation(orderRequest, userId);
        logResponseInfo("handleOrderConfirmation", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping(value = "/fetch/{userId}")
    public ResponseEntity<ServiceResponse> getOrderDetails(@PathVariable Integer userId){
        logRequestInfo("getOrderDetails", userId);
        ServiceResponse response = service.getOrderDetail(userId);
        logResponseInfo("handleOrderConfirmation", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void logRequestInfo(String methodName, Object requestData) {
        log.info("Initial Request from {}: {}", methodName, kv("request", requestData));
    }

    private void logResponseInfo(String methodName, ServiceResponse responseData) {
        log.info("Final Response from {}: {}", methodName, kv("response", responseData));
    }
}
