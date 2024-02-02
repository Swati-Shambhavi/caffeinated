package com.caffeinated.cartexpressoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean available;
    private String imagePath;
    private Double discountPercentage;
    private LocalDate discountStartDate;
    private LocalDate discountEndDate;
}
