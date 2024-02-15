package com.caffeinated.cartexpressoservice.service;

import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.CartItemRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.UserDto;

public interface ICartService{
     ServiceResponse getCartDetails(Integer userId);
     ServiceResponse addToCart(Integer userId, CartItemRequest itemDto) throws Exception;
     ServiceResponse removeFromCart(Integer userId, Integer productId) throws Exception;
    }