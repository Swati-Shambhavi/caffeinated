package com.caffeinated.cartexpressoservice.controller;

import com.caffeinated.cartexpressoservice.entity.User;
import com.caffeinated.cartexpressoservice.model.CartItemRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.UserDto;
import com.caffeinated.cartexpressoservice.service.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController {
	private ICartService service;

	@GetMapping("/{userId}")
	public ServiceResponse getCartDetails(@PathVariable String userId) {
//		return service.getCartDetails(CommonUtility.getCurrentUser());
		return service.getCartDetails(userId);
	}
//
	@PostMapping("/{userEmail}")
	public ServiceResponse addToCart(@RequestBody CartItemRequest item, @PathVariable String userEmail) throws Exception {
		return service.addToCart(userEmail, item);
	}
//
//	@DeleteMapping("/{userEmail}")
//	public ServiceResponse removeFromCart(@RequestParam Integer productId, @PathVariable String userEmail) throws Exception {
//		return service.removeFromCart(userEmail, productId);
//	}
}