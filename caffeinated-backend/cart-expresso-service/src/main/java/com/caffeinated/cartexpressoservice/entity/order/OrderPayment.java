package com.caffeinated.cartexpressoservice.entity.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "razorpay_confirmations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "razorpay_order_id", nullable = false)
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id", nullable = false)
    private String razorpayPaymentId;

    @Column(name = "razorpay_signature", nullable = false)
    private String razorpaySignature;

}
