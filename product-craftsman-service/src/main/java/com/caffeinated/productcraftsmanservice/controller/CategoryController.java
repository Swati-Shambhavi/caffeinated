package com.caffeinated.productcraftsmanservice.controller;

import com.caffeinated.productcraftsmanservice.model.CategoryRequest;
import com.caffeinated.productcraftsmanservice.model.ServiceResponse;
import com.caffeinated.productcraftsmanservice.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories/api")
@AllArgsConstructor
public class CategoryController {
	private ICategoryService categoryService;

	@PostMapping
	public ServiceResponse addCategory(@RequestBody CategoryRequest category) throws Exception {
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
	public ServiceResponse updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest)
			throws Exception {
		return categoryService.updateCategory(categoryId, categoryRequest);
	}
}
