package com.caffeinated.cartexpressoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private Integer cart;
    private ProductDto product;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}
