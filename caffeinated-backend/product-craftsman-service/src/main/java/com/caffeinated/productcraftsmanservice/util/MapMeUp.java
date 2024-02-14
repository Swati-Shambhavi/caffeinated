package com.caffeinated.productcraftsmanservice.util;

import com.caffeinated.productcraftsmanservice.dto.CategoryResponse;
import com.caffeinated.productcraftsmanservice.dto.ProductResponse;
import com.caffeinated.productcraftsmanservice.entity.Category;
import com.caffeinated.productcraftsmanservice.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapMeUp {
    public static CategoryResponse toCategoryResponse(Category entity) {
        if (entity == null) {
            return null;
        }
        List<ProductResponse> productResponses = new ArrayList<>();
        if(entity.getProducts()!=null){
            productResponses = entity.getProducts()
                    .stream()
                    .map(MapMeUp::toProductResponse)
                    .collect(Collectors.toList());
        }

        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .products(productResponses)
                .build();
    }

    public static ProductResponse toProductResponse(Product entity) {
        if (entity == null) {
            return null;
        }

        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .available(entity.isAvailable())
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .imagePath(entity.getImagePath())
                .discountPercentage(entity.getDiscountPercentage())
                .discountStartDate(entity.getDiscountStartDate())
                .discountEndDate(entity.getDiscountEndDate())
                .build();
    }
}
