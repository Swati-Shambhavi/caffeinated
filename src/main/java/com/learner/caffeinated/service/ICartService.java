package com.learner.caffeinated.service;

import com.learner.caffeinated.dto.CartItemDto;
import com.learner.caffeinated.entity.ServiceResponse;

public interface ICartService{
     ServiceResponse getCartDetails(String userEmail);
     ServiceResponse addToCart(String userEmail, CartItemDto itemDto) throws Exception;
     ServiceResponse removeFromCart(String userEmail, Integer productId) throws Exception;
    }