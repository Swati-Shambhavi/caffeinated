package com.caffeinated.cartexpressoservice.model;

import lombok.Data;

@Data
public class CartItemDto {
    private Integer id;
    private Integer cart;
    private ProductDto product;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}
