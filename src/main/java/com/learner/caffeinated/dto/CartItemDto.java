package com.learner.caffeinated.dto;

import com.learner.caffeinated.entity.Cart;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartItemDto{
	private Integer productId;
	private Integer quantity;
}
