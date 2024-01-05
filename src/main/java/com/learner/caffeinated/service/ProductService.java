package com.learner.caffeinated.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learner.caffeinated.dto.ProductDto;
import com.learner.caffeinated.entity.Category;
import com.learner.caffeinated.entity.Product;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.repo.CategoryRepository;
import com.learner.caffeinated.repo.ProductRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ImageCompressionService imageCompressionService;

	public ServiceResponse getAllProducts() throws Exception {
		ServiceResponse response = ServiceResponse.builder().build();
		List<Product> allProducts = productRepo.findAll();
		response.setData(allProducts);
		return response;
	}

	public ServiceResponse addNewProduct(ProductDto productDto) throws Exception {
		ServiceResponse response = ServiceResponse.builder().build();
		Optional<Category> category = categoryRepo.findById(productDto.getCategoryId());
		if (!category.isPresent()) {
			throw new Exception("No category found with id:" + productDto.getCategoryId());
		}
		// Save the image to the static folder
		String imagePath = "images/" + productDto.getImage().getOriginalFilename();
		byte[] compressedImage = imageCompressionService.compressImage(productDto.getImage(), 0.8f);
		Files.write(Paths.get("src/main/resources/static/" + imagePath), compressedImage);
//		

		Product product = mapProductDetails(productDto, category.get());
		product.setImagePath(imagePath);
		response.setData(productRepo.save(product));
		return response;
	}

	private Product mapProductDetails(ProductDto dto, Category category) {
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
		if (!product.isPresent()) {
			throw new Exception("No Product found with id:" + productId);
		}
		return ServiceResponse.builder().data(product.get()).build();
	}

	public ServiceResponse deleteProduct(Integer productId) throws Exception {
		Optional<Product> product = productRepo.findById(productId);
		if (!product.isPresent()) {
			throw new Exception("No category found with id:" + productId);
		}
		if (StringUtils.isNotBlank(product.get().getImagePath())) {
			deleteImage(product.get().getImagePath());
		}
		productRepo.delete(product.get());
		return ServiceResponse.builder().data("Product deleted successfully!").build();
	}

	public ServiceResponse updateProduct(Integer productId, ProductDto productDto) throws Exception {
		Optional<Product> __product = productRepo.findById(productId);
		if (!__product.isPresent()) {
			throw new Exception("No category found with id:" + productId);
		}
		Product product = __product.get();
		Category category = null;
		if (productDto.getCategoryId() != null && productDto.getCategoryId() > 0) {
			Optional<Category> _category = categoryRepo.findById(productDto.getCategoryId());
			if (!_category.isPresent()) {
				throw new Exception("No category found with id:" + productDto.getCategoryId());
			}
			category = _category.get();
		}
		mapUpdateProductDetails(productDto, product, category);
		return ServiceResponse.builder().data(productRepo.save(product)).build();
	}

	private void mapUpdateProductDetails(ProductDto dto, Product product, Category category) throws IOException {
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
	            throw new IOException("Image file not found: " + imageFilePath.toString());
	        }
	    }
}
