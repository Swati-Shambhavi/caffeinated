package com.caffeinated.cartexpressoservice.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDetails {
    private Integer orderId;
    private double totalPrice;
    private List<OrderItemDetails> orderItems;
    private LocalDateTime orderedOn;
}