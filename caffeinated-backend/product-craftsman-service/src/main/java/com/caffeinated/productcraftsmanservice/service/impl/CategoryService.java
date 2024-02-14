package com.caffeinated.productcraftsmanservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.caffeinated.productcraftsmanservice.dto.CategoryResponse;
import com.caffeinated.productcraftsmanservice.entity.Category;
import com.caffeinated.productcraftsmanservice.exception.ResourceNotFoundException;
import com.caffeinated.productcraftsmanservice.dto.CategoryRequest;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;
import com.caffeinated.productcraftsmanservice.repo.CategoryRepository;
import com.caffeinated.productcraftsmanservice.service.ICategoryService;
import com.caffeinated.productcraftsmanservice.util.MapMeUp;
import lombok.AllArgsConstructor;
import static net.logstash.logback.argument.StructuredArguments.kv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;

    public ServiceResponse addNewCategory(CategoryRequest categoryDto) {
        ServiceResponse response = ServiceResponse.builder().build();
        Category category = categoryRepository.save(Category.builder().name(categoryDto.getCategoryName()).build());
        log.info("Saved the categories to repo {}", kv("category",category));
        response.setData(MapMeUp.toCategoryResponse(category));
        return response;
    }

    public ServiceResponse getAllCategories() {
        ServiceResponse response = ServiceResponse.builder().build();
        List<Category> categories= categoryRepository.findAll();
        log.info("Categories from repo {}", kv("categories",categories));
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(MapMeUp::toCategoryResponse)
                .collect(Collectors.toList());
        response.setData(categoryResponses);
        return response;
    }

    public ServiceResponse getCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("No category found with id:","categoryId",categoryId.toString());
        }
        return ServiceResponse.builder().data(MapMeUp.toCategoryResponse(category.get())).build();
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
        log.info("origin category from repo {}", kv("category",category));
        category.setName(categoryRequest.getCategoryName());
        category = categoryRepository.save(category);
        log.info("Updated the category to repo {}", kv("category",category));
        return ServiceResponse.builder().data(MapMeUp.toCategoryResponse(category)).build();
    }

}
