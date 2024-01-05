package com.learner.caffeinated.controller;

import com.learner.caffeinated.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learner.caffeinated.dto.ProductDto;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.service.impl.ProductService;
import com.learner.caffeinated.util.ProductDtoConverter;
import com.learner.caffeinated.validation.PostValidation;
import com.learner.caffeinated.validation.PutValidation;

@RestController
@RequestMapping("/public/product")
@AllArgsConstructor
public class ProductController {
	private IProductService productService;

	@GetMapping
	public ServiceResponse getAllProducts() throws Exception {
		return productService.getAllProducts();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ServiceResponse addProduct(@Validated(PostValidation.class) @RequestPart("product") ProductDto product,
			@RequestPart("image") MultipartFile image) throws Exception {
//		ProductDto product = ProductDtoConverter.convertJsonAndImageToProductDto(json, image);
		product.setImage(image);
		return productService.addNewProduct(product);
	}

	@GetMapping("/{productId}")
	public ServiceResponse getProduct(@PathVariable Integer productId) throws Exception {
		return productService.getProduct(productId);
	}

	@DeleteMapping("/{productId}")
	public ServiceResponse deleteProduct(@PathVariable Integer productId) throws Exception {
		return productService.deleteProduct(productId);
	}

//	@PutMapping("/{productId}")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{productId}")
	public ServiceResponse updateProduct(@PathVariable Integer productId,
			@Validated(PutValidation.class) @RequestPart(required=false, value="product") ProductDto product,
			@RequestPart(required=false, value="image") MultipartFile image) throws Exception {
		//In case only the image needs to be updated, there is no need to send the product json in request
		if(product==null) {
			product=ProductDto.builder().build();
		}
		product.setImage(image);
		return productService.updateProduct(productId, product);
	}
}
