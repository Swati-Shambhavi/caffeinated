package com.caffeinated.cartexpressoservice.model.externalservice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class ProductResponseData {
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
