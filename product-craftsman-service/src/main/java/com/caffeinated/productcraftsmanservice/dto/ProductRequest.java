package com.caffeinated.productcraftsmanservice.dto;
import java.time.LocalDate;

import com.caffeinated.productcraftsmanservice.validation.PostValidation;
import jakarta.validation.constraints.Null;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
	@NotNull(groups = PostValidation.class)
	private String name;
	@NotNull(groups = PostValidation.class)
	private String description;
	@NotNull(groups = PostValidation.class)
	private Double price;
	@NotNull(groups = PostValidation.class)
	private Integer stockQuantity;
	@NotNull(groups = PostValidation.class)
	private Boolean available;
	@NotNull(groups = PostValidation.class)
	private Integer categoryId;
//	@NotNull(groups = PostValidation.class)
	private MultipartFile image;

	private Double discountPercentage;

	private LocalDate discountStartDate;

	private LocalDate discountEndDate;
}
