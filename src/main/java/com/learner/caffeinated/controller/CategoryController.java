package com.learner.caffeinated.controller;

import com.learner.caffeinated.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learner.caffeinated.dto.CategoryDto;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.service.impl.CategoryService;

@RestController
@RequestMapping(value="/public/category")
@AllArgsConstructor
public class CategoryController {
	private ICategoryService categoryService;

	@PostMapping
	public ServiceResponse addCategory(@RequestBody CategoryDto category) throws Exception {
		return categoryService.addNewCategory(category);
	}
	
	@GetMapping
	public ServiceResponse getAllCategory() {
		return categoryService.getAllCategories();
	}
	
	@GetMapping("/{categoryId}")
	public ServiceResponse getCategory(@PathVariable Integer categoryId) throws Exception {
		return categoryService.getCategories(categoryId);
	}
	
	@DeleteMapping("/{categoryId}")
	public ServiceResponse deleteCategory(@PathVariable Integer categoryId) throws Exception {
		return categoryService.deleteCategory(categoryId);
	}
	
	@PutMapping("/{categoryId}")
	public ServiceResponse updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDto categoryDto) throws Exception {
		return categoryService.updateCategory(categoryId, categoryDto);
	}
}
