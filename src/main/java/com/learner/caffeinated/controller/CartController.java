package com.learner.caffeinated.controller;

import com.learner.caffeinated.service.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learner.caffeinated.dto.CartItemDto;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.util.CommonUtility;

@RestController
@RequestMapping("/private/cart")
@AllArgsConstructor
public class CartController {
	private ICartService service;
	
	@GetMapping
	public ServiceResponse getCartDetails() {
		return service.getCartDetails(CommonUtility.getCurrentUser());
	}  
	
	@PostMapping
	public ServiceResponse addToCart(@RequestBody CartItemDto item) throws Exception {
		return service.addToCart(CommonUtility.getCurrentUser(), item);
	}
	
	@DeleteMapping("/")
	public ServiceResponse removeFromCart(@RequestParam Integer productId) throws Exception {
		return service.removeFromCart(CommonUtility.getCurrentUser(), productId);
	}
}
