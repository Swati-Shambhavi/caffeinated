package com.caffeinated.cartexpressoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {
    private Integer id;
    private List<CartItemDto> cartItems;
    private Double totalPrice;
}
