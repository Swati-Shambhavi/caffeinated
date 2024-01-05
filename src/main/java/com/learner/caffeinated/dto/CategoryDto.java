package com.learner.caffeinated.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
	@NotBlank
	private String categoryName;
}
