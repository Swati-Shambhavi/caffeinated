package com.caffeinated.cartexpressoservice.model.cart;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
public class CartItemRequest{
	private Integer productId;
	private Integer quantity;

	public CartItemRequest(Integer productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public CartItemRequest(Integer productId){
		this.productId = productId;
	}
}
