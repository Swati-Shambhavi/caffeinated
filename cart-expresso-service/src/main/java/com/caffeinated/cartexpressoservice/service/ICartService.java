package com.caffeinated.cartexpressoservice.service;

import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.CartItemRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.UserDto;

public interface ICartService{
     ServiceResponse getCartDetails(String email);
     ServiceResponse addToCart(String userEmail, CartItemRequest itemDto) throws Exception;
     ServiceResponse removeFromCart(String userEmail, Integer productId) throws Exception;
    }