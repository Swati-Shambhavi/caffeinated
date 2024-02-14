package com.caffeinated.cartexpressoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private Integer productId;
    private String productName;
    private String productDescription;
    private Double price;
    private String imagePath;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}