package com.caffeinated.cartexpressoservice.controller;

import com.caffeinated.cartexpressoservice.model.CartItemRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.service.ICartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping("/carts/api")
@AllArgsConstructor
@Slf4j
public class CartController {
	private final ICartService service;

	@GetMapping("/{userId}")
	public ServiceResponse getCartDetails(@PathVariable Integer userId) {
		logRequestInfo("getCartDetails", userId);
		ServiceResponse response = service.getCartDetails(userId);
		logResponseInfo("getCartDetails", response);
		return response;
	}

	@PostMapping("/{userId}")
	public ServiceResponse addToCart(@RequestBody CartItemRequest item, @PathVariable Integer userId) throws Exception {
		logRequestInfo("addToCart", item);
		ServiceResponse response = service.addToCart(userId, item);
		logResponseInfo("addToCart", response);
		return response;
	}

	@DeleteMapping("/{userId}")
	public ServiceResponse removeFromCart(@RequestBody CartItemRequest request, @PathVariable Integer userId) throws Exception {
		logRequestInfo("removeFromCart", request);
		ServiceResponse response = service.removeFromCart(userId, request.getProductId());
		logResponseInfo("removeFromCart", response);
		return response;
	}

	private void logRequestInfo(String methodName, Object requestData) {
		log.info("Initial Request from {}: {}", methodName, kv("request", requestData));
	}

	private void logResponseInfo(String methodName, ServiceResponse responseData) {
		log.info("Final Response from {}: {}", methodName, kv("response", responseData));
	}
}
