package com.caffeinated.cartexpressoservice.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private double price;

	@Column(name = "stock_quantity", nullable = false)
	private int stockQuantity;

	@Column(nullable = false)
	private boolean available;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	@JsonBackReference
	private Category category;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "discount_percentage")
	private double discountPercentage;

	@Column(name = "discount_start_date")
	private LocalDate discountStartDate;

	@Column(name = "discount_end_date")
	private LocalDate discountEndDate;
}
