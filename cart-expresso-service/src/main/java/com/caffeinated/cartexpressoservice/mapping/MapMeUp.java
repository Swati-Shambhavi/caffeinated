package com.caffeinated.cartexpressoservice.mapping;

import com.caffeinated.cartexpressoservice.entity.Cart;
import com.caffeinated.cartexpressoservice.entity.Product;
import com.caffeinated.cartexpressoservice.entity.User;
import com.caffeinated.cartexpressoservice.model.CartDto;
import com.caffeinated.cartexpressoservice.model.ProductDto;
import com.caffeinated.cartexpressoservice.model.UserDto;

public class MapMeUp {
    public static Product toProductEntity(ProductDto productDto){
        return Product.builder().build();
    }

    public static User toUserEntity(UserDto userDto) {
        return User.builder().build();
    }
    public static Cart toCartEntity(CartDto cart) {
        return Cart.builder().build();
    }
}
