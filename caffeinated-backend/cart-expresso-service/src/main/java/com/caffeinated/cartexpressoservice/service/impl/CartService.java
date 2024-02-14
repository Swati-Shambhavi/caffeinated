package com.caffeinated.cartexpressoservice.service.impl;
import com.caffeinated.cartexpressoservice.entity.Cart;
import com.caffeinated.cartexpressoservice.entity.CartItem;
import com.caffeinated.cartexpressoservice.exception.ExternalServiceException;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.mapping.MapMeUp;
import com.caffeinated.cartexpressoservice.model.*;
import com.caffeinated.cartexpressoservice.repo.CartRepository;
import com.caffeinated.cartexpressoservice.service.ICartService;
import com.caffeinated.cartexpressoservice.service.client.CaffeinatedPersonaFeignClient;
import com.caffeinated.cartexpressoservice.service.externalservice.CaffeinatedPersonaExternalService;
import com.caffeinated.cartexpressoservice.service.externalservice.ProductCraftsmanExternalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements ICartService {
	private CartRepository cartRepo;
	private ProductCraftsmanExternalService productExternalService;
	private CaffeinatedPersonaExternalService userExternalService;


	public ServiceResponse getCartDetails(String email) {
		Optional<Cart> cart = cartRepo.findByUserEmail(email);
		ServiceResponse response = ServiceResponse.builder().build();
		List<CartItemDto> cartItemDto = new ArrayList<>();
		CartResponse cartResponse = CartResponse.builder().totalPrice(0d).cartItems(cartItemDto).build();
		if (cart.isEmpty()) {
			log.info("No cart detail found with this email");
			response.setData(cartResponse);
		} else {
			setCartResponseDetail(cartResponse, response, cart.get());

		}
		return response;
	}

	private void setCartResponseDetail(CartResponse cartResponse, ServiceResponse response, Cart cart) {
		cartResponse.setTotalPrice(cart.getTotalPrice());
		List<CartItemDto> cartItemDtoList = cartResponse.getCartItems();
		cart.getCartItems().forEach(cartItem -> {
			CartItemDto cartItemDto = CartItemDto.builder()
					.productName(cartItem.getProduct().getName())
					.productId(cartItem.getProduct().getId())
					.productDescription(cartItem.getProduct().getDescription())
					.price(cartItem.getProduct().getPrice())
					.quantity(cartItem.getQuantity())
					.unitPrice(cartItem.getUnitPrice())
					.totalPrice(cartItem.getTotalPrice())
					.imagePath(cartItem.getProduct().getImagePath())
					.build();
			cartItemDtoList.add(cartItemDto);
		});
		cartResponse.setCartItems(cartItemDtoList);
	}

	@Transactional
	public ServiceResponse addToCart(String userEmail, CartItemRequest itemDto) throws Exception {
		UserDto userDto = userExternalService.getUser(userEmail);
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
		ProductDto product = productExternalService.getProduct(itemDto.getProductId());

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
		UserDto userDto = userExternalService.getUser(userEmail);
		if (userDto.getCart() == null || userDto.getCart().getCartItems().isEmpty()) {
			throw new EntityNotFoundException("Your cart is empty!");
		}
		// Check if the product exists
		ProductDto product = productExternalService.getProduct(productId);
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