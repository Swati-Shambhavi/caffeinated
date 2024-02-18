package com.caffeinated.cartexpressoservice.repo;

import com.caffeinated.cartexpressoservice.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<List<Order>> findByUserId(Integer userId);
}
