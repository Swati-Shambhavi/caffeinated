package com.learner.caffeinated.service;

import com.learner.caffeinated.dto.ProductDto;
import com.learner.caffeinated.entity.ServiceResponse;

import java.io.IOException;

public interface IProductService {
     ServiceResponse getAllProducts() throws Exception;
     ServiceResponse addNewProduct(ProductDto productDto) throws Exception;
     ServiceResponse getProduct(Integer productId) throws Exception;
     ServiceResponse deleteProduct(Integer productId) throws Exception;
     ServiceResponse updateProduct(Integer productId, ProductDto productDto) throws Exception;
    }
