package com.caffeinated.cartexpressoservice.model.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CartResponse {
    private List<CartItemDto> cartItems;
    private Integer userId;
    private Double totalPrice;
}
