package com.caffeinated.productcraftsmanservice.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.caffeinated.productcraftsmanservice.dto.*;
import com.caffeinated.productcraftsmanservice.dto.openapi.GenerateImageRequest;
import com.caffeinated.productcraftsmanservice.dto.openapi.GenerateImageResponse;
import com.caffeinated.productcraftsmanservice.entity.Category;
import com.caffeinated.productcraftsmanservice.entity.Product;
import com.caffeinated.productcraftsmanservice.exception.ResourceNotFoundException;
import com.caffeinated.productcraftsmanservice.queue.ProductStockUpdateMessage;
import com.caffeinated.productcraftsmanservice.repo.CategoryRepository;
import com.caffeinated.productcraftsmanservice.repo.ProductRepository;
import com.caffeinated.productcraftsmanservice.service.IProductService;
import com.caffeinated.productcraftsmanservice.service.client.ImageGeneratorFeignClient;
import com.caffeinated.productcraftsmanservice.util.AiResponseMapper;
import com.caffeinated.productcraftsmanservice.util.MapMeUp;
import static net.logstash.logback.argument.StructuredArguments.kv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService {
	private ProductRepository productRepo;
	private CategoryRepository categoryRepo;
	private final WebClient webClient;
	private final ImageGeneratorFeignClient imageGenerator;

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

	@Override
	public void updateProductStock(ProductStockUpdateMessage message) {
		Product product = productRepo.findById(message.getProductId()).get();
		product.setStockQuantity(product.getStockQuantity()-message.getQuantity());
		Product savedProduct = productRepo.save(product);
		log.info("Product's stock got updated {}",kv("savedProduct",savedProduct));
	}
	@Override
	public CustomizedProductResponse customizeProduct(ProductCustomizationRequest request) {
		String key = "AIzaSyAqy5ng0jmM-QURmhlNcZAHUXePV6GECvE";
		String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + key;
		Map<String, Object> requestData = createRequestData(request);
		log.info("Request Data: {}", requestData);
		CustomizedProductResponse mappedResponse = null;

		long startTime = System.currentTimeMillis();

		try {
			String responseBody = webClient.post()
					.uri(apiUrl)
					.header("Content-Type", "application/json")
					.body(BodyInserters.fromValue(requestData))
					.retrieve()
					.bodyToMono(String.class)
					.block();

			long endTime = System.currentTimeMillis();

			log.info("Raw Response Data: {}", responseBody);
			mappedResponse = AiResponseMapper.mapToCustomizedProductResponse(responseBody);

			log.info("Mapped Response Data: {}", mappedResponse);

			long duration = endTime - startTime;
			log.info("Gemini API call took {} milliseconds", duration);

		} catch (WebClientResponseException.BadRequest ex) {
			log.error("Bad Request Exception: {}", ex.getResponseBodyAsString());
			throw ex;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(false && null!=mappedResponse){
			GenerateImageRequest imageGeneratorRequest = GenerateImageRequest.builder()
					.prompt(mappedResponse.getProductDescription())
					.numImages(1)
					.size("512x512")
					.build();
			try{
				GenerateImageResponse response = imageGenerator.generateImage(imageGeneratorRequest);
				mappedResponse.setImageUrl(response.getData().get(0).getUrl());
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return mappedResponse;
	}

	private Map<String, Object> createRequestData(ProductCustomizationRequest request) {
		Map<String, Object> requestData = new HashMap<>();
		requestData.put("contents", List.of(
				Map.of("parts", Collections.singletonList(
						Map.of("text", String.format(
								"Create a customized food product that provides %s. It should be %s and %s. The format should be %s with a caffeine level of %s. Ideally, it should stay within a budget of %s (optional).\n\nProvide the response in the following format:\n\n* Product Name: [ProductName]\n* Product Description: [ProductDescription]\n* Image URL: [ImageUrl]\n* Ingredients: [Ingredients]\n* Caffeine Content: [CaffeineContent]\n* Recipe: [Recipe]. \nMake sure to not use any unnecessary special characters. \nUse only * to separate the required response data levels. Use only # to list down the different parts within Ingredients. \nUse only | symbol to list down the different steps within Recipe section. \n Important note is that do not use any other special character or numbers for listing.",
								String.join(", ", request.getDesiredEffects()),
								String.join(", ", request.getPreferredFlavors()),
								String.join(", ", request.getDietaryRestrictions()),
								request.getDesiredFormat(),
								request.getCaffeineLevel(),
								request.getBudget() != null ? "within " + request.getBudget() + " budget" : "unspecified budget"
						))
				))
		));
		return requestData;
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
