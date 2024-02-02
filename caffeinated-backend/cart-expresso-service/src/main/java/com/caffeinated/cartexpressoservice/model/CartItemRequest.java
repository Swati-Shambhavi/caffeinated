package com.caffeinated.cartexpressoservice.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartItemRequest{
	private Integer productId;
	private Integer quantity;
}
