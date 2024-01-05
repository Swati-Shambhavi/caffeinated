package com.learner.caffeinated.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learner.caffeinated.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
