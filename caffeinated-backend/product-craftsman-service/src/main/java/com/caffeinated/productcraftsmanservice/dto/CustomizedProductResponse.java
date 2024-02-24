package com.caffeinated.productcraftsmanservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomizedProductResponse {
    private String productName;
    private String productDescription;
    private String imageUrl;
    private List<String> ingredients;
    private String caffeineContent;
    private List<String> recipeSteps;
}
