package com.caffeinated.cartexpressoservice.repo;

import com.caffeinated.cartexpressoservice.entity.order.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Integer> {
}
