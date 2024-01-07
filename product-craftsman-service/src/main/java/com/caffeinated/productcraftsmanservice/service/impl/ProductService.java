package com.caffeinated.productcraftsmanservice.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.caffeinated.productcraftsmanservice.entity.Category;
import com.caffeinated.productcraftsmanservice.entity.Product;
import com.caffeinated.productcraftsmanservice.model.ProductRequest;
import com.caffeinated.productcraftsmanservice.model.ServiceResponse;
import com.caffeinated.productcraftsmanservice.repo.CategoryRepository;
import com.caffeinated.productcraftsmanservice.repo.ProductRepository;
import com.caffeinated.productcraftsmanservice.service.IProductService;
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
	private ImageCompressionService imageCompressionService;

	public ServiceResponse getAllProducts() {
		ServiceResponse response = ServiceResponse.builder().build();
		List<Product> allProducts = productRepo.findAll();
		response.setData(allProducts);
		return response;
	}

	public ServiceResponse addNewProduct(ProductRequest productRequest) throws Exception {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Category> category = categoryRepo.findById(productRequest.getCategoryId());
		if (category.isEmpty()) {
			throw new Exception("No category found with id:" + productRequest.getCategoryId());
		}
		// Save the image to the static folder
		String imagePath = "images/" + productRequest.getImage().getOriginalFilename();
		byte[] compressedImage = imageCompressionService.compressImage(productRequest.getImage(), 0.8f);
		Files.write(Paths.get("src/main/resources/static/" + imagePath), compressedImage);
//		

		Product product = mapProductDetails(productRequest, category.get());
		product.setImagePath(imagePath);
		response.setData(productRepo.save(product));
		return response;
	}

	private Product mapProductDetails(ProductRequest dto, Category category) {
		Product product = Product.builder().name(dto.getName()).description(dto.getDescription()).price(dto.getPrice())
				.category(category).stockQuantity(dto.getStockQuantity()).available(dto.getAvailable()).build();
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

	public ServiceResponse getProduct(Integer productId) throws Exception {
		Optional<Product> product = productRepo.findById(productId);
		if (product.isEmpty()) {
			throw new Exception("No Product found with id:" + productId);
		}
		return ServiceResponse.builder().data(product.get()).build();
	}

	public ServiceResponse deleteProduct(Integer productId) throws Exception {
		Optional<Product> product = productRepo.findById(productId);
		if (product.isEmpty()) {
			throw new Exception("No category found with id:" + productId);
		}
		if (StringUtils.isNotBlank(product.get().getImagePath())) {
			deleteImage(product.get().getImagePath());
		}
		productRepo.delete(product.get());
		return ServiceResponse.builder().data("Product deleted successfully!").build();
	}

	public ServiceResponse updateProduct(Integer productId, ProductRequest productRequest) throws Exception {
		Optional<Product> __product = productRepo.findById(productId);
		if (__product.isEmpty()) {
			throw new Exception("No category found with id:" + productId);
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
		return ServiceResponse.builder().data(productRepo.save(product)).build();
	}

	private void mapUpdateProductDetails(ProductRequest dto, Product product, Category category) throws IOException {
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
		if (dto.getImage() != null) {
			if (StringUtils.isNotBlank(product.getImagePath())) {
				deleteImage(product.getImagePath());
			}
			String imagePath = "images/" + dto.getImage().getOriginalFilename();
			byte[] compressedImage = imageCompressionService.compressImage(dto.getImage(), 0.8f);
			Files.write(Paths.get("src/main/resources/static/" + imagePath), compressedImage);
			product.setImagePath(imagePath);
		}
	}

	  private void deleteImage(String imagePath) throws IOException {
	        Path imageFilePath = Paths.get("src/main/resources/static/" + imagePath);

	        // Check if the file exists before attempting deletion
	        if (Files.exists(imageFilePath)) {
	            Files.delete(imageFilePath);
	        } else {
	            // Log or throw an exception if the file doesn't exist
//	            throw new IOException("Image file not found: " + imageFilePath);
				log.error("Image not found at location "+imageFilePath);
	        }
	    }
}
