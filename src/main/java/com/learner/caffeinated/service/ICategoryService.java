package com.learner.caffeinated.service;

import com.learner.caffeinated.dto.CategoryDto;
import com.learner.caffeinated.entity.ServiceResponse;

public interface ICategoryService {

    ServiceResponse addNewCategory(CategoryDto categoryDto);
    ServiceResponse getAllCategories();
    ServiceResponse getCategories(Integer categoryId) throws Exception;
    ServiceResponse deleteCategory(Integer categoryId) throws Exception ;
    ServiceResponse updateCategory(Integer categoryId, CategoryDto categoryDto) throws Exception;
    }
