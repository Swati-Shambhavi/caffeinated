package com.caffeinated.cartexpressoservice.service;

import com.caffeinated.cartexpressoservice.model.cart.CartItemRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;

public interface ICartService{
     ServiceResponse getCartDetails(Integer userId);
     ServiceResponse addToCart(Integer userId, CartItemRequest itemDto) throws Exception;
     ServiceResponse removeFromCart(Integer userId, Integer productId) throws Exception;
    }