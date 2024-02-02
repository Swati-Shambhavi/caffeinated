package com.caffeinated.productcraftsmanservice.controller;

import com.caffeinated.productcraftsmanservice.dto.CategoryRequest;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;
import com.caffeinated.productcraftsmanservice.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping(value = "/categories/api")
@AllArgsConstructor
@Slf4j
public class CategoryController {
	private final ICategoryService categoryService;

	@PostMapping
	public ResponseEntity<ServiceResponse> addCategory( @Valid @RequestBody CategoryRequest category)  {
		logRequestInfo("addCategory", category);
		ServiceResponse response = categoryService.addNewCategory(category);
		logResponseInfo("addCategory", response);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ServiceResponse> getAllCategory() {
		logRequestInfo("getAllCategory", null);
		ServiceResponse response = categoryService.getAllCategories();
		logResponseInfo("getAllCategory", response);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<ServiceResponse> getCategory(@PathVariable Integer categoryId)  {
		logRequestInfo("getCategory", categoryId);
		ServiceResponse response = categoryService.getCategories(categoryId);
		logResponseInfo("getCategory", response);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ServiceResponse> deleteCategory(@PathVariable Integer categoryId)  {
		logRequestInfo("deleteCategory", categoryId);
		ServiceResponse response = categoryService.deleteCategory(categoryId);
		logResponseInfo("deleteCategory", response);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<ServiceResponse> updateCategory(@PathVariable Integer categoryId,
														  @Valid @RequestBody CategoryRequest categoryRequest)  {
		logRequestInfo("updateCategory", categoryId);
		ServiceResponse response = categoryService.updateCategory(categoryId, categoryRequest);
		logResponseInfo("updateCategory", response);
		return ResponseEntity.ok(response);
	}

	// Helper methods to log request and response information
	private void logRequestInfo(String methodName, Object requestData) {
		log.info("Initial Request from {}: {}", methodName, kv("request", requestData));
	}

	private void logResponseInfo(String methodName, ServiceResponse responseData) {
		log.info("Final Response from {}: {}", methodName, kv("response", responseData));
	}
}
