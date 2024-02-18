package com.caffeinated.cartexpressoservice.model.order;

import com.caffeinated.cartexpressoservice.entity.order.Order;
import com.caffeinated.cartexpressoservice.entity.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderConfirmationResponse {
    private Boolean isOrderConfirmed;
    private OrderDetails orderDetails;
}
