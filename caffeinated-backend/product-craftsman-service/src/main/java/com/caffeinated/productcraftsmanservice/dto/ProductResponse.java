package com.caffeinated.productcraftsmanservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean available;
    private String categoryName;
    private String imagePath;
    private Double discountPercentage;
    private LocalDate discountStartDate;
    private LocalDate discountEndDate;
}
