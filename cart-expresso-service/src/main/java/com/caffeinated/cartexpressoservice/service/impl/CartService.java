package com.caffeinated.cartexpressoservice.service.impl;
import com.caffeinated.cartexpressoservice.entity.Cart;
import com.caffeinated.cartexpressoservice.entity.CartItem;
import com.caffeinated.cartexpressoservice.entity.Product;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.mapping.MapMeUp;
import com.caffeinated.cartexpressoservice.model.CartItemRequest;
import com.caffeinated.cartexpressoservice.model.ProductDto;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.UserDto;
import com.caffeinated.cartexpressoservice.repo.CartRepository;
import com.caffeinated.cartexpressoservice.service.ICartService;
import com.caffeinated.cartexpressoservice.service.client.CaffeinatedPersonaFeignClient;
import com.caffeinated.cartexpressoservice.service.client.ProductCraftsmanFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements ICartService {
	private CaffeinatedPersonaFeignClient caffeinatedPersonaFeignClient;
	private CartRepository cartRepo;
	private ProductCraftsmanFeignClient productCraftsmanFeignClient;

	private UserDto userDetailExternalServiceCall(String email)
	{
		ServiceResponse userDetail = caffeinatedPersonaFeignClient.getUserDetail(email);
		UserDto userDto;
		if(userDetail.getData()==null){
			throw new ResourceNotFoundException("User","email",email);
		}else{
			ObjectMapper objectMapper = new ObjectMapper();
			userDto = objectMapper.convertValue(userDetail.getData(), UserDto.class);
			return userDto;
		}
	}

	private ProductDto productDetailExternalServiceCall(Integer productId) throws Exception {
		ServiceResponse productDetail = productCraftsmanFeignClient.getProduct(productId);
		ProductDto productDto;
		if(productDetail.getData()==null){
			throw new ResourceNotFoundException("Product","productId",productId.toString());
		}else{
			ObjectMapper objectMapper = new ObjectMapper();
			productDto = objectMapper.convertValue(productDetail.getData(), ProductDto.class);
			return productDto;
		}
	}

	public ServiceResponse getCartDetails(String email) {
		Optional<Cart> cart = cartRepo.findByUserEmail(email);
		ServiceResponse response = ServiceResponse.builder().build();
//
		if (cart.isEmpty()) {
			response.setData("Your cart is empty.");
		} else {
			response.setData(cart.get());
		}
		return response;
	}

	@Transactional
	public ServiceResponse addToCart(String userEmail, CartItemRequest itemDto) throws Exception {
//		Optional<Cart> _cart = cartRepo.findByUserEmail(userEmail);
		UserDto userDto = userDetailExternalServiceCall(userEmail);
		Cart cart;
		if (userDto.getCart()==null) {
			// Create a new Cart
			// Add the cartItem to that cart
			cart = Cart.builder().totalPrice(0).user(MapMeUp.toUserEntity(userDto, false)).cartItems(new HashSet<>()).build();
		} else {
			cart = MapMeUp.toCartEntity(userDto.getCart(), userDto);
		}


		// Check if the product exists
		//Call MS2 to get product details

		ProductDto product = productDetailExternalServiceCall(itemDto.getProductId());

		//Check the available stockQuantity of the Product
		if(itemDto.getQuantity() > product.getStockQuantity()) {
			if(product.getStockQuantity() == 0) {
				throw new Exception("This Product is out of stock at this moment!");
			}
			throw new Exception("Currently we have "+product.getStockQuantity()+" item(s) in the stock");
		}

		boolean productAlreadyExistsInCart = false;
		for (CartItem cartItem : cart.getCartItems()) {
			if (itemDto.getProductId().equals(cartItem.getProduct().getId())) {
				// If a particular product is already there in cart, just increase the quantity
				// of that item
				cartItem.setQuantity(cartItem.getQuantity() + itemDto.getQuantity());
				productAlreadyExistsInCart = true;
				break;
			}
		}
		if (!productAlreadyExistsInCart) {
			CartItem newCartItem = CartItem.builder().cart(cart).product(MapMeUp.toProductEntity(product)).quantity(itemDto.getQuantity())
					.unitPrice(product.getPrice()).build();
			cart.getCartItems().add(newCartItem);
		}
		// Set total price for each cart item
		double totalCartPrice = 0;
		for (CartItem cartItem : cart.getCartItems()) {
			cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQuantity());
			totalCartPrice += cartItem.getTotalPrice();
		}
		cart.setTotalPrice(totalCartPrice);
//		user.setCart(cart);
		//Call MS1 to save user details ********************************************************
//		user = userRepo.save(user);

		cartRepo.save(cart);
		return ServiceResponse.builder().data(cart).build();

	}

	public ServiceResponse removeFromCart(String userEmail, Integer productId) throws Exception {
		UserDto userDto = userDetailExternalServiceCall(userEmail);
//		User user = userRepo.findByEmail(userEmail).get(0);
		if (userDto.getCart() == null || userDto.getCart().getCartItems().isEmpty()) {
			throw new EntityNotFoundException("Your cart is empty!");
		}
		// Check if the product exists
		ProductDto product = productDetailExternalServiceCall(productId);
//		productRepo.findById(productId)
//				.orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + productId));
		Cart cart = MapMeUp.toCartEntity(userDto.getCart(), userDto);
		Set<CartItem> cartItems = cart.getCartItems();
		Iterator<CartItem> iterator = cartItems.iterator();
		double totalCartPrice = cart.getTotalPrice();
		boolean productNotInCart = true;
		while(iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if(cartItem.getProduct().getId().equals(productId)) {
				productNotInCart = false;
				totalCartPrice -= cartItem.getUnitPrice();
				//Quantity of that product = 1 -> In that case remove the CartItem from the cart && update the totalCartPrice
				if(cartItem.getQuantity() == 1) {
					iterator.remove();
				}
				//Quantity of that product > 1 -> Decrease the quantity of product && update the totalCartItemPrice && totalCartPrice
				else if(cartItem.getQuantity() > 1) {
					cartItem.setQuantity(cartItem.getQuantity() - 1);
					cartItem.setTotalPrice(cartItem.getTotalPrice() - cartItem.getUnitPrice());
				}
			}
		}
		if(productNotInCart) {
			throw new Exception("Couldn't find this product in your cart.");
		}
		cart.setCartItems(cartItems);
		cart.setTotalPrice(totalCartPrice);
//		user.setCart(cart);
//		userRepo.save(user);
		cartRepo.save(cart);
		return ServiceResponse.builder().data(cart).build();
	}

}
