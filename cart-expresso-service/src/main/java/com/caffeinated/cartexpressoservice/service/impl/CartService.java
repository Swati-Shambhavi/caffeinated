package com.caffeinated.cartexpressoservice.service.impl;

import com.caffeinated.cartexpressoservice.entity.User;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.caffeinated.cartexpressoservice.model.UserDto;
import com.caffeinated.cartexpressoservice.service.ICartService;
import com.caffeinated.cartexpressoservice.service.client.CaffeinatedPersonaFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements ICartService {
	private CaffeinatedPersonaFeignClient caffeinatedPersonaFeignClient;

	public UserDto userDetailExternalServiceCall(String email)
	{
		ServiceResponse userDetail = caffeinatedPersonaFeignClient.getUserDetail(email);
		UserDto userDto = null;
		if(userDetail.getData()==null){
			throw new ResourceNotFoundException("User","email",email);
		}else{
			ObjectMapper objectMapper = new ObjectMapper();
			userDto = objectMapper.convertValue(userDetail.getData(), UserDto.class);
			return userDto;
		}
	}

//	public ServiceResponse getCartDetails(String userEmail) {
////		User user = userRepo.findByEmail(userEmail).get(0);
//		//Call MS1 to get user detail
//
//		User user = null;
//		ServiceResponse response = ServiceResponse.builder().build();
//		if (user.getCart() == null) {
//			response.setData("Your cart is empty.");
//		} else {
//			response.setData(user.getCart());
//		}
//
//		return response;
//	}

//	@Transactional
//	public ServiceResponse addToCart(String userEmail, CartItemRequest itemDto) throws Exception {
//		//Call MS1 to get user detail
//		User user = null;
////		User user = userRepo.findByEmail(userEmail).get(0);
//		Cart cart ;
//		if (user.getCart() == null) {
//			// Create a new Cart
//			// Add the cartItem to that cart
//			cart = Cart.builder().user(user).totalPrice(0).cartItems(new HashSet<>()).build();
//		} else {
//			cart = user.getCart();
//		}
//
//		// Check if the product exists
//		//Call MS2 to get product details
//		Product product =null;
////		Product product = productRepo.findById(itemDto.getProductId())
////				.orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + itemDto.getProductId()));
//
//		//Check the available stockQuantity of the Product
//		if(itemDto.getQuantity() > product.getStockQuantity()) {
//			if(product.getStockQuantity() == 0) {
//				throw new Exception("This Product is out of stock at this moment!");
//			}
//			throw new Exception("Currently we have "+product.getStockQuantity()+" item(s) in the stock");
//		}
//
//		boolean productAlreadyExistsInCart = false;
//		for (CartItem cartItem : cart.getCartItems()) {
//			if (itemDto.getProductId().equals(cartItem.getProduct().getId())) {
//				// If a particular product is already there in cart, just increase the quantity
//				// of that item
//				cartItem.setQuantity(cartItem.getQuantity() + itemDto.getQuantity());
//				productAlreadyExistsInCart = true;
//				break;
//			}
//		}
//		if (!productAlreadyExistsInCart) {
//			CartItem newCartItem = CartItem.builder().cart(cart).product(product).quantity(itemDto.getQuantity())
//					.unitPrice(product.getPrice()).build();
////			newCartItem.setTotalPrice(newCartItem.getQuantity() * newCartItem.getUnitPrice());
//			cart.getCartItems().add(newCartItem);
//		}
//		// Set total price for each cart item
//		double totalCartPrice = 0;
//		for (CartItem cartItem : cart.getCartItems()) {
//			cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQuantity());
//			totalCartPrice += cartItem.getTotalPrice();
//		}
//		cart.setTotalPrice(totalCartPrice);
//		user.setCart(cart);
//		//Call MS1 to save user details ********************************************************
//		user = userRepo.save(user);
//
//		return ServiceResponse.builder().data(user.getCart().getCartItems()).build();
//
//	}
//
//	public ServiceResponse removeFromCart(String userEmail, Integer productId) throws Exception {
//		User user = userRepo.findByEmail(userEmail).get(0);
//		if (user.getCart() == null || user.getCart().getCartItems().isEmpty()) {
//			throw new EntityNotFoundException("Your cart is empty!");
//		}
//		// Check if the product exists
//		productRepo.findById(productId)
//				.orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + productId));
//		Cart cart = user.getCart();
//		Set<CartItem> cartItems = cart.getCartItems();
//		Iterator<CartItem> iterator = cartItems.iterator();
//		double totalCartPrice = cart.getTotalPrice();
//		boolean productNotInCart = true;
//		while(iterator.hasNext()) {
//			CartItem cartItem = iterator.next();
//			if(cartItem.getProduct().getId().equals(productId)) {
//				productNotInCart = false;
//				totalCartPrice -= cartItem.getUnitPrice();
//				//Quantity of that product = 1 -> In that case remove the CartItem from the cart && update the totalCartPrice
//				if(cartItem.getQuantity() == 1) {
//					iterator.remove();
//				}
//				//Quantity of that product > 1 -> Decrease the quantity of product && update the totalCartItemPrice && totalCartPrice
//				else if(cartItem.getQuantity() > 1) {
//					cartItem.setQuantity(cartItem.getQuantity() - 1);
//					cartItem.setTotalPrice(cartItem.getTotalPrice() - cartItem.getUnitPrice());
//				}
//			}
//		}
//		if(productNotInCart) {
//			throw new Exception("Couldn't find this product in your cart.");
//		}
//		cart.setCartItems(cartItems);
//		cart.setTotalPrice(totalCartPrice);
//		user.setCart(cart);
//		userRepo.save(user);
//		return ServiceResponse.builder().data(user.getCart()).build();
//	}

}
