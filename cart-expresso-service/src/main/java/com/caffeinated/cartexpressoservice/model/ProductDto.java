package com.caffeinated.cartexpressoservice.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean available;
    private List<Integer> items;
    private String imagePath;
    private Double discountPercentage;
    private String discountStartDate;
    private String discountEndDate;
}
