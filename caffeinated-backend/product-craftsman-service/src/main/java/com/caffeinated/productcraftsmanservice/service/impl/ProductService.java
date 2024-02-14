package com.caffeinated.productcraftsmanservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.caffeinated.productcraftsmanservice.dto.ProductResponse;
import com.caffeinated.productcraftsmanservice.entity.Category;
import com.caffeinated.productcraftsmanservice.entity.Product;
import com.caffeinated.productcraftsmanservice.dto.ProductRequest;
import com.caffeinated.productcraftsmanservice.dto.ServiceResponse;
import com.caffeinated.productcraftsmanservice.exception.ResourceNotFoundException;
import com.caffeinated.productcraftsmanservice.repo.CategoryRepository;
import com.caffeinated.productcraftsmanservice.repo.ProductRepository;
import com.caffeinated.productcraftsmanservice.service.IProductService;
import com.caffeinated.productcraftsmanservice.util.MapMeUp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService {
	private ProductRepository productRepo;
	private CategoryRepository categoryRepo;

	public ServiceResponse getAllProducts() {
		ServiceResponse response = ServiceResponse.builder().build();
		List<Product> allProducts = productRepo.findAll();
		List<ProductResponse> productsList = allProducts.stream().map(MapMeUp::toProductResponse).collect(Collectors.toList());
		response.setData(productsList);
		return response;
	}

	public ServiceResponse addNewProduct(ProductRequest productRequest) {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Category> category = categoryRepo.findById(productRequest.getCategoryId());
		if (category.isEmpty()) {
			throw new ResourceNotFoundException("No category found with id:" , "categoryId", productRequest.getCategoryId().toString());
		}
		Product product = mapProductDetails(productRequest, category.get());
		response.setData(MapMeUp.toProductResponse(productRepo.save(product)));
		return response;
	}

	private Product mapProductDetails(ProductRequest dto, Category category) {
		Product product = Product.builder().name(dto.getName()).description(dto.getDescription()).price(dto.getPrice())
				.category(category).stockQuantity(dto.getStockQuantity()).available(dto.getAvailable()).imagePath(dto.getImagePath()).build();
		if (dto.getDiscountEndDate() != null) {
			product.setDiscountEndDate(dto.getDiscountEndDate());
		}
		if (dto.getDiscountStartDate() != null) {
			product.setDiscountStartDate(dto.getDiscountStartDate());
		}
		if (dto.getDiscountPercentage() != null && dto.getDiscountPercentage() > 0) {
			product.setDiscountPercentage(dto.getDiscountPercentage());
		}
		return product;
	}

	public ServiceResponse getProduct(Integer productId) {
		Optional<Product> product = productRepo.findById(productId);
		if (product.isEmpty()) {
			throw new ResourceNotFoundException("No Product found with id:", "productId",productId.toString());
		}
		return ServiceResponse.builder().data(MapMeUp.toProductResponse(product.get())).build();
	}

	public ServiceResponse deleteProduct(Integer productId)  {
		Optional<Product> product = productRepo.findById(productId);
		if (product.isEmpty()) {
			throw new ResourceNotFoundException("No Product found with id:", "productId",productId.toString());
		}
		productRepo.delete(product.get());
		return ServiceResponse.builder().data("Product deleted successfully!").build();
	}

	public ServiceResponse updateProduct(Integer productId, ProductRequest productRequest) throws Exception {
		Optional<Product> __product = productRepo.findById(productId);
		if (__product.isEmpty()) {
			throw new Exception("No product found with id:" + productId);
		}
		Product product = __product.get();
		Category category = null;
		if (productRequest.getCategoryId() != null && productRequest.getCategoryId() > 0) {
			Optional<Category> _category = categoryRepo.findById(productRequest.getCategoryId());
			if (_category.isEmpty()) {
				throw new Exception("No category found with id:" + productRequest.getCategoryId());
			}
			category = _category.get();
		}
		mapUpdateProductDetails(productRequest, product, category);
		return ServiceResponse.builder().data(MapMeUp.toProductResponse(productRepo.save(product))).build();
	}

	private void mapUpdateProductDetails(ProductRequest dto, Product product, Category category)  {
		if (category != null) {
			product.setCategory(category);
		}
		if (StringUtils.isNotBlank(dto.getDescription())) {
			product.setDescription(dto.getDescription());
		}
		if (StringUtils.isNotBlank(dto.getName())) {
			product.setName(dto.getName());
		}
		if (dto.getDiscountEndDate() != null) {
			product.setDiscountEndDate(dto.getDiscountEndDate());
		}
		if (dto.getDiscountStartDate() != null) {
			product.setDiscountStartDate(dto.getDiscountStartDate());
		}
		if (dto.getDiscountPercentage() != null && dto.getDiscountPercentage() > 0) {
			product.setDiscountPercentage(dto.getDiscountPercentage());
		}
		if (dto.getPrice() != null && dto.getPrice() > 0) {
			product.setPrice(dto.getPrice());
		}
		if (dto.getStockQuantity() != null) {
			product.setStockQuantity(dto.getStockQuantity());
		}
		if (dto.getAvailable() != null) {
			product.setAvailable(dto.getAvailable());
		}
		if (dto.getImagePath() != null) {
			product.setImagePath(dto.getImagePath());
		}
	}
}
