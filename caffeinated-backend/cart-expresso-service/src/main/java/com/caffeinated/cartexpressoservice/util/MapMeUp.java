package com.caffeinated.cartexpressoservice.util;

import com.caffeinated.cartexpressoservice.entity.Cart;
import com.caffeinated.cartexpressoservice.entity.CartItem;
import com.caffeinated.cartexpressoservice.model.CartItemDto;
import com.caffeinated.cartexpressoservice.model.CartResponse;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class  MapMeUp{
    public static CartResponse toCartResponse(Cart cart) {
        if(cart==null) return  null;
        List<CartItemDto> cartItems= new ArrayList<>();
        if(!CollectionUtils.isEmpty(cart.getCartItems())){
            cartItems = cart.getCartItems().stream().map(MapMeUp::toCartItemDto).collect(Collectors.toList());
        }
        return CartResponse.builder()
                .userId(cart.getUserId())
                .totalPrice(cart.getTotalPrice())
                .cartItems(cartItems)
                .build();
    }
public static CartItemDto toCartItemDto(CartItem ci) {
    if(ci == null){
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


}