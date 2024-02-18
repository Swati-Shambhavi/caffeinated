package com.caffeinated.cartexpressoservice.repo;

import com.caffeinated.cartexpressoservice.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByUserId(Integer userId);
}
