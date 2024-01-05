package com.learner.caffeinated.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learner.caffeinated.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
