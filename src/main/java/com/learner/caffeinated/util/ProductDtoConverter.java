package com.learner.caffeinated.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learner.caffeinated.dto.ProductDto;

public class ProductDtoConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ProductDto convertJsonAndImageToProductDto(String json, MultipartFile image) throws IOException {
        ProductDto productDto = objectMapper.readValue(json, ProductDto.class);
        productDto.setImage(image);  // Set the image bytes
        return productDto;
    }
}
