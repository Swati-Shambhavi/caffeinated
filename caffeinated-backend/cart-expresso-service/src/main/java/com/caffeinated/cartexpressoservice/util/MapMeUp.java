package com.caffeinated.cartexpressoservice.util;

import com.caffeinated.cartexpressoservice.entity.cart.Cart;
import com.caffeinated.cartexpressoservice.entity.cart.CartItem;
import com.caffeinated.cartexpressoservice.entity.order.Order;
import com.caffeinated.cartexpressoservice.entity.order.OrderItem;
import com.caffeinated.cartexpressoservice.model.cart.CartItemDto;
import com.caffeinated.cartexpressoservice.model.cart.CartResponse;
import com.caffeinated.cartexpressoservice.model.order.OrderDetails;
import com.caffeinated.cartexpressoservice.model.order.OrderItemDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapMeUp {
    public static CartResponse toCartResponse(Cart cart) {
        if (cart == null) return null;
        List<CartItemDto> cartItems = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cart.getCartItems())) {
            cartItems = cart.getCartItems().stream().map(MapMeUp::toCartItemDto).collect(Collectors.toList());
        }
        return CartResponse.builder()
                .userId(cart.getUserId())
                .totalPrice(cart.getTotalPrice())
                .cartItems(cartItems)
                .build();
    }

    public static CartItemDto toCartItemDto(CartItem ci) {
        if (ci == null) {
            return null;
        }
        return CartItemDto
                .builder()
                .id(ci.getId())
                .productId(ci.getProductId())
                .productName(ci.getProductName())
                .unitPrice(ci.getUnitPrice())
                .quantity(ci.getProductQuantity())
                .totalPrice(ci.getTotalPrice())
                .build();
    }

    public static OrderDetails toOrderDetails(Order order) {
        if (order == null) return null;

        List<OrderItemDetails> orderItemDetails = new ArrayList<>();
        if (!CollectionUtils.isEmpty(order.getOrderItems())) {
            orderItemDetails = order.getOrderItems().stream().map(MapMeUp::toOrderItemDetails).collect(Collectors.toList());
        }

        return OrderDetails.builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderItems(orderItemDetails)
                .orderedOn(order.getCreatedAt())
                .build();
    }

    public static OrderItemDetails toOrderItemDetails(OrderItem orderItem) {
        if (orderItem == null) return null;

        return OrderItemDetails.builder()
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .productQuantity(orderItem.getProductQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
