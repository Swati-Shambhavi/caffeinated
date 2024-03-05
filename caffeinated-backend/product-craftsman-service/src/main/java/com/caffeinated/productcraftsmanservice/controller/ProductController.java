package com.caffeinated.productcraftsmanservice.controller;

import com.caffeinated.productcraftsmanservice.dto.CustomizedProductResponse;
import com.caffeinated.productcraftsmanservice.dto.ProductCustomizationRequest;
import com.caffeinated.productcraftsmanservice.dto.ProductRequest;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;
import com.caffeinated.productcraftsmanservice.service.IProductService;
import com.caffeinated.productcraftsmanservice.validation.PostValidation;
import com.caffeinated.productcraftsmanservice.validation.PutValidation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequestMapping("/products/api")
@AllArgsConstructor
@Slf4j
public class ProductController {
	private final IProductService productService;

	@GetMapping
	public ResponseEntity<ServiceResponse> getAllProducts() throws Exception {
		logRequestInfo("getAllProducts", null);
		ServiceResponse response = productService.getAllProducts();
		logResponseInfo("getAllProducts", response);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<ServiceResponse> addProduct(@Validated(PostValidation.class) @RequestBody ProductRequest product) throws Exception {
		logRequestInfo("addProduct", product);
		ServiceResponse response = productService.addNewProduct(product);
		logResponseInfo("addProduct", response);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ServiceResponse> getProduct(@PathVariable Integer productId) throws Exception {
		logRequestInfo("getProduct", productId);
		ServiceResponse response = productService.getProduct(productId);
		logResponseInfo("getProduct", response);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ServiceResponse> deleteProduct(@PathVariable Integer productId) throws Exception {
		logRequestInfo("deleteProduct", productId);
		ServiceResponse response = productService.deleteProduct(productId);
		logResponseInfo("deleteProduct", response);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ServiceResponse> updateProduct(@PathVariable Integer productId,
														 @Validated(PutValidation.class) @RequestBody ProductRequest product) throws Exception {
		logRequestInfo("updateProduct", product);
		ServiceResponse response = productService.updateProduct(productId, product);
		logResponseInfo("updateProduct", response);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/customize")
	public ResponseEntity<CustomizedProductResponse> customizeProduct(@RequestBody @Valid ProductCustomizationRequest request) {
		logRequestInfo("customizeProduct", request);
		CustomizedProductResponse response = productService.customizeProduct(request);
		logResponseInfo("customizeProduct", response);
		return ResponseEntity.ok(response);
	}

	private void logRequestInfo(String methodName, Object requestData) {
		log.info("Initial Request from {}: {}", methodName, kv("request", requestData));
	}

	private void logResponseInfo(String methodName, Object responseData) {
		log.info("Final Response from {}: {}", methodName, kv("response", responseData));
	}
}
