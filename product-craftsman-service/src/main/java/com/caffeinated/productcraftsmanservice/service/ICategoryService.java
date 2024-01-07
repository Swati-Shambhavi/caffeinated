package com.caffeinated.productcraftsmanservice.service;

import com.caffeinated.productcraftsmanservice.model.CategoryRequest;
import com.caffeinated.productcraftsmanservice.model.ServiceResponse;

public interface ICategoryService {

    ServiceResponse addNewCategory(CategoryRequest categoryDto);
    ServiceResponse getAllCategories();
    ServiceResponse getCategories(Integer categoryId) throws Exception;
    ServiceResponse deleteCategory(Integer categoryId) throws Exception ;
    ServiceResponse updateCategory(Integer categoryId, CategoryRequest categoryDto) throws Exception;
    }
