package com.learner.caffeinated.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learner.caffeinated.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
