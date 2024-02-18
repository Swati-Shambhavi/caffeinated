package com.caffeinated.cartexpressoservice.entity.cart;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;
	@Column
	private Integer productId;
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)
	private int productQuantity;
	private double unitPrice;
	private double totalPrice;

}
