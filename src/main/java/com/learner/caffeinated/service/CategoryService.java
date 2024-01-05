package com.learner.caffeinated.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learner.caffeinated.dto.CategoryDto;
import com.learner.caffeinated.entity.Category;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.repo.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public ServiceResponse addNewCategory(CategoryDto categoryDto) throws Exception {
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

	public ServiceResponse getCategories(Integer categoryId) throws Exception {
		Optional<Category> category = categoryRepository.findById(categoryId);
		if(!category.isPresent()) {
			throw new Exception("No category found with id:"+categoryId);
		}
		return ServiceResponse.builder().data(category.get()).build();
	}

	public ServiceResponse deleteCategory(Integer categoryId) throws Exception {
		Optional<Category> category = categoryRepository.findById(categoryId);
		if(!category.isPresent()) {
			throw new Exception("No category found with id:"+categoryId);
		}
		categoryRepository.delete(category.get());
		return ServiceResponse.builder().data("Category deleted successfully!").build();
	}

	public ServiceResponse updateCategory(Integer categoryId, CategoryDto categoryDto) throws Exception {
		Optional<Category> __category = categoryRepository.findById(categoryId);
		if(!__category.isPresent()) {
			throw new Exception("No category found with id:"+categoryId);
		}
		Category category=__category.get();
		category.setName(categoryDto.getCategoryName());
		categoryRepository.save(category);
		return ServiceResponse.builder().data("Sucessfully updated!").build();
	}

}
