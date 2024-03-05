package com.caffeinated.productcraftsmanservice.dto.openapi;

import lombok.Data;

import java.util.List;

@Data
public class GenerateImageResponse {
    private List<GeneratedImage> data;
}