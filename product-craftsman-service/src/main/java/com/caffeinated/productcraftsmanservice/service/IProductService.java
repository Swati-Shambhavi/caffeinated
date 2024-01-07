package com.caffeinated.productcraftsmanservice.service;

import com.caffeinated.productcraftsmanservice.model.ProductRequest;
import com.caffeinated.productcraftsmanservice.model.ServiceResponse;

public interface IProductService {
     ServiceResponse getAllProducts() throws Exception;
     ServiceResponse addNewProduct(ProductRequest productRequest) throws Exception;
     ServiceResponse getProduct(Integer productId) throws Exception;
     ServiceResponse deleteProduct(Integer productId) throws Exception;
     ServiceResponse updateProduct(Integer productId, ProductRequest productRequest) throws Exception;
    }
