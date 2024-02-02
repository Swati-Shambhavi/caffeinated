package com.caffeinated.productcraftsmanservice.repo;


import com.caffeinated.productcraftsmanservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
