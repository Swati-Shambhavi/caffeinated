package com.caffeinated.cartexpressoservice.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderItemDetails {
    private Integer productId;
    private String productName;
    private int productQuantity;
    private double unitPrice;
    private double totalPrice;
}
