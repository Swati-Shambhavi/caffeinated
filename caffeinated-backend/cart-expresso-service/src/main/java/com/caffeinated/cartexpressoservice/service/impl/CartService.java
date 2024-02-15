package com.caffeinated.cartexpressoservice.service.impl;
import com.caffeinated.cartexpressoservice.config.ServiceConfig;
import com.caffeinated.cartexpressoservice.entity.Cart;
import com.caffeinated.cartexpressoservice.entity.CartItem;
import com.caffeinated.cartexpressoservice.model.*;
import com.caffeinated.cartexpressoservice.model.externalservice.ProductResponseData;
import com.caffeinated.cartexpressoservice.repo.CartRepository;
import com.caffeinated.cartexpressoservice.service.ICartService;
import com.caffeinated.cartexpressoservice.service.externalservice.ProductCraftsmanExternalService;
import com.caffeinated.cartexpressoservice.util.MapMeUp;
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
	private ServiceConfig serviceConfig;
	private ProductCraftsmanExternalService productExternalService;

	public ServiceResponse getCartDetails(Integer userId) {
		Optional<Cart> _cart = cartRepo.findByUserId(userId);
		Cart cart ;
		if(_cart.isEmpty()){
			cart = setAndSaveEmptyCartForThisUser(userId);
		}else{
			cart = _cart.get();
		}
		ServiceResponse response = ServiceResponse.builder().build();
		response.setData(MapMeUp.toCartResponse(cart));
		return response;
	}

	private Cart setAndSaveEmptyCartForThisUser(Integer userId) {
		List<CartItem> cartItems = new ArrayList<>();
		return cartRepo.save(Cart.builder().userId(userId).cartItems(cartItems).totalPrice(0d).build());
	}

	@Transactional
	public ServiceResponse addToCart(Integer userId, CartItemRequest itemDto) throws Exception {
		Optional<Cart> _cart = cartRepo.findByUserId(userId);
		Cart cart ;
		if(_cart.isEmpty()){
			cart = setAndSaveEmptyCartForThisUser(userId);
		}else{
			cart = _cart.get();
		}
		// Check if the product exists
		ProductResponseData product = productExternalService.getProduct(itemDto.getProductId());

		//Check the available stockQuantity of the Product
		if(itemDto.getQuantity() > product.getStockQuantity()) {
			if(product.getStockQuantity() == 0) {
				throw new Exception("This Product is out of stock at this moment!");
			}
			throw new Exception("Currently we have "+product.getStockQuantity()+" item(s) in the stock");
		}

		boolean productAlreadyExistsInCart = false;
		for (CartItem cartItem : cart.getCartItems()) {
			if (itemDto.getProductId().equals(cartItem.getProductId())) {
				cartItem.setProductQuantity(cartItem.getProductQuantity() + itemDto.getQuantity());
				productAlreadyExistsInCart = true;
				break;
			}
		}
		if (!productAlreadyExistsInCart) {
			CartItem newCartItem = CartItem.builder()
					.productId(product.getId())
					.productName(product.getName())
					.cart(cart)
					.productQuantity(itemDto.getQuantity())
					.unitPrice(product.getPrice())
					.build();
			cart.getCartItems().add(newCartItem);
		}
		// Set total price for each cart item
		double totalCartPrice = 0;
		for (CartItem cartItem : cart.getCartItems()) {
			cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getProductQuantity());
			totalCartPrice += cartItem.getTotalPrice();
		}
		cart.setTotalPrice(totalCartPrice);
		cartRepo.save(cart);
		return ServiceResponse.builder().data(MapMeUp.toCartResponse(cart)).build();

	}

	public ServiceResponse removeFromCart(Integer userId, Integer productId) {
		Optional<Cart> _cart = cartRepo.findByUserId(userId);
		Cart cart ;
		if(_cart.isEmpty() || _cart.get().getCartItems().size()==0){
			Map<String, String> errorDetail = new HashMap<>();
			errorDetail.put("600001","Either cart is empty or cart detail could not be found for the user:"+userId);
			ErrorData error = ErrorData.builder().code(serviceConfig.getBusinessException().getCode()).message(serviceConfig.getBusinessException().getDescription()).errors(errorDetail).build();
			return ServiceResponse.builder().error(error).build();
		}else{
			cart = _cart.get();
		}
		List<CartItem> cartItems = cart.getCartItems();
		Iterator<CartItem> iterator = cartItems.iterator();
		double totalCartPrice = cart.getTotalPrice();
		boolean productNotInCart = true;
		while(iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if(cartItem.getProductId().equals(productId)) {
				productNotInCart = false;
				totalCartPrice -= cartItem.getUnitPrice();
				//Quantity of that product = 1 -> In that case remove the CartItem from the cart && update the totalCartPrice
				if(cartItem.getProductQuantity() == 1) {
					iterator.remove();
				}
				//Quantity of that product > 1 -> Decrease the quantity of product && update the totalCartItemPrice && totalCartPrice
				else if(cartItem.getProductQuantity() > 1) {
					cartItem.setProductQuantity(cartItem.getProductQuantity() - 1);
					cartItem.setTotalPrice(cartItem.getTotalPrice() - cartItem.getUnitPrice());
				}
			}
		}
		if(productNotInCart) {
			Map<String, String> errorDetail = new HashMap<>();
			errorDetail.put("600002","No product found in your cart with id:"+productId);
			ErrorData error = ErrorData.builder().code(serviceConfig.getBusinessException().getCode()).message(serviceConfig.getBusinessException().getDescription()).errors(errorDetail).build();
			return ServiceResponse.builder().error(error).build();
		}
		cart.setCartItems(cartItems);
		cart.setTotalPrice(totalCartPrice);
		cartRepo.save(cart);
		return ServiceResponse.builder().data(MapMeUp.toCartResponse(cart)).build();
	}

}
