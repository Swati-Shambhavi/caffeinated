package com.caffeinated.cartexpressoservice.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "unit_price", nullable = false, precision = 10)
    private double unitPrice;

    @Column(name = "total_price", nullable = false, precision = 10)
    private double totalPrice;
}
