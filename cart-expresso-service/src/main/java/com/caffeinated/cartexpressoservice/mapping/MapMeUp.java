package com.caffeinated.cartexpressoservice.mapping;

import com.caffeinated.cartexpressoservice.entity.*;
import com.caffeinated.cartexpressoservice.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapMeUp {
    public static Product toProductEntity(ProductDto dto){
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .discountEndDate(dto.getDiscountEndDate())
                .discountStartDate(dto.getDiscountStartDate())
                .discountPercentage(dto.getDiscountPercentage())
                .price(dto.getPrice())
                .imagePath(dto.getImagePath())
                .stockQuantity(dto.getStockQuantity())
                .build();
    }

    public static User toUserEntity(UserDto userDto, boolean isCartRequired) {
        return User.builder()
                .id(userDto.getId())
                .address(userDto.getAddress()!=null?toAddressEntity(userDto.getAddress()):null)
                .cart(isCartRequired && userDto.getCart()!=null?toCartEntity(userDto.getCart(), userDto):null)
                .email(userDto.getEmail())
                .mobileNumber(userDto.getMobileNumber())
//                .password(userDto.g)
                .role(userDto.getRole())
                .build();

    }

    public static Cart toCartEntity(CartDto cartDto, UserDto userDto) {
        Cart cart= Cart.builder()
                .id(cartDto.getId())
//        .user(userDto!=null?MapMeUp.toUserEntity(userDto):null)
                .totalPrice(cartDto.getTotalPrice())
                .build();
        userDto.setCart(cartDto);
        cart.setUser(userDto!=null?MapMeUp.toUserEntity(userDto, false):null);
        cart.setCartItems(cartDto.getCartItems()!=null?toCartItemsList(cartDto.getCartItems(), cart):null);
        return cart;
    }
    public static Address toAddressEntity(AddressDto dto)
    {
        return Address.builder()
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .addressId(dto.getAddressId())
                .city(dto.getCity())
                .state(dto.getState())
                .pinCode(dto.getPinCode())
                .build();
    }
    private static Set<CartItem> toCartItemsList(List<CartItemDto> cartItemsDto, Cart cart) {
        Set<CartItem> cartItems = new HashSet<>();
        cartItemsDto.forEach(dto -> {
            CartItem cartItem = CartItem.builder()
                    .id(dto.getId())
                    .product(toProductEntity(dto.getProduct()))
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice())
                    .cart(cart)
                    .build();
            cartItems.add(cartItem);
        });
        return cartItems;
    }
}
