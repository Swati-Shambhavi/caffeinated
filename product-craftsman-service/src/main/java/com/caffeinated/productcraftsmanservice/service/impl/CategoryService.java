package com.caffeinated.productcraftsmanservice.service.impl;


import java.util.Optional;

import com.caffeinated.productcraftsmanservice.entity.Category;
import com.caffeinated.productcraftsmanservice.exception.ResourceNotFoundException;
import com.caffeinated.productcraftsmanservice.dto.CategoryRequest;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;
import com.caffeinated.productcraftsmanservice.repo.CategoryRepository;
import com.caffeinated.productcraftsmanservice.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;

    public ServiceResponse addNewCategory(CategoryRequest categoryDto) {
        ServiceResponse response = ServiceResponse.builder().build();
        Category category = Category.builder().name(categoryDto.getCategoryName()).build();
        response.setData(categoryRepository.save(category));
        return response;
    }

    public ServiceResponse getAllCategories() {
        ServiceResponse response = ServiceResponse.builder().build();
        response.setData(categoryRepository.findAll());
        return response;
    }

    public ServiceResponse getCategories(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("No category found with id:","categoryId",categoryId.toString());
        }
        return ServiceResponse.builder().data(category.get()).build();
    }

    public ServiceResponse deleteCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("No category found with id:","categoryId",categoryId.toString());
        }
        categoryRepository.delete(category.get());
        return ServiceResponse.builder().data("Category deleted successfully!").build();
    }

    public ServiceResponse updateCategory(Integer categoryId, CategoryRequest categoryRequest)  {
        Optional<Category> __category = categoryRepository.findById(categoryId);
        if(__category.isEmpty()) {
            throw new ResourceNotFoundException("No category found with id:","categoryId",categoryId.toString());
        }
        Category category=__category.get();
        category.setName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return ServiceResponse.builder().data("Successfully updated!").build();
    }

}
