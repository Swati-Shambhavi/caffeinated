package com.caffeinated.productcraftsmanservice.service;

import com.caffeinated.productcraftsmanservice.dto.CategoryRequest;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;

public interface ICategoryService {

    ServiceResponse addNewCategory(CategoryRequest categoryDto);
    ServiceResponse getAllCategories();
    ServiceResponse getCategory(Integer categoryId) ;
    ServiceResponse deleteCategory(Integer categoryId) ;
    ServiceResponse updateCategory(Integer categoryId, CategoryRequest categoryDto);
    }
