package com.learner.caffeinated.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learner.caffeinated.dto.CartItemDto;
import com.learner.caffeinated.entity.Cart;
import com.learner.caffeinated.entity.CartItem;
import com.learner.caffeinated.entity.Product;
import com.learner.caffeinated.entity.ServiceResponse;
import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.repo.CartRepository;
import com.learner.caffeinated.repo.ProductRepository;
import com.learner.caffeinated.repo.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CartService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CartRepository cartRepository;

	public ServiceResponse getCartDetails(String userEmail) {
		User user = userRepo.findByEmail(userEmail).get(0);
		ServiceResponse response = ServiceResponse.builder().build();
		if (user.getCart() == null) {
			response.setData("Your cart is empty.");
		} else {
			response.setData(user.getCart());
		}

		return response;
	}

	@Transactional
	public ServiceResponse addToCart(String userEmail, CartItemDto itemDto) throws Exception {
		User user = userRepo.findByEmail(userEmail).get(0);
		Cart cart = null;
		if (user.getCart() == null) {
			// Create a new Cart
			// Add the cartItem to that cart
			cart = Cart.builder().user(user).totalPrice(0).cartItems(new HashSet<>()).build();
		} else {
			cart = user.getCart();
		}

		// Check if the product exists
		Product product = productRepo.findById(itemDto.getProductId())
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + itemDto.getProductId()));
		
		//Check the available stockQuantity of the Product
		if(itemDto.getQuantity() > product.getStockQuantity()) {
			if(product.getStockQuantity() == 0) {
				throw new Exception("This Product is out of stock at this moment!");
			}
			throw new Exception("Currently we have "+product.getStockQuantity()+" item(s) in the stock");
		}
		
		boolean productAlreadyExistsInCart = false;
		for (CartItem cartItem : cart.getCartItems()) {
			if (itemDto.getProductId() == cartItem.getProduct().getId()) {
				// If a particular product is already there in cart, just increase the quantity
				// of that item
				cartItem.setQuantity(cartItem.getQuantity() + itemDto.getQuantity());
				productAlreadyExistsInCart = true;
				break;
			}
		}
		if (!productAlreadyExistsInCart) {
			CartItem newCartItem = CartItem.builder().cart(cart).product(product).quantity(itemDto.getQuantity())
					.unitPrice(product.getPrice()).build();
//			newCartItem.setTotalPrice(newCartItem.getQuantity() * newCartItem.getUnitPrice());
			cart.getCartItems().add(newCartItem);
		}
		// Set total price for each cart item
		double totalCartPrice = 0;
		for (CartItem cartItem : cart.getCartItems()) {
			cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQuantity());
			totalCartPrice += cartItem.getTotalPrice();
		}
		cart.setTotalPrice(totalCartPrice);
		user.setCart(cart);
		user = userRepo.save(user);

		ServiceResponse response = ServiceResponse.builder().data(user.getCart().getCartItems()).build();

		return response;
	}

	public ServiceResponse removeFromCart(String userEmail, Integer productId) throws Exception {
		User user = userRepo.findByEmail(userEmail).get(0);
		if (user.getCart() == null || user.getCart().getCartItems().isEmpty()) {
			throw new EntityNotFoundException("Your cart is empty!");
		}
		// Check if the product exists
		productRepo.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id:" + productId));
		Cart cart = user.getCart();
		Set<CartItem> cartItems = cart.getCartItems();
		Iterator<CartItem> iterator = cartItems.iterator();
		double totalCartPrice = cart.getTotalPrice();
		boolean productNotInCart = true;
		while(iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if(cartItem.getProduct().getId() == productId) {
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
		user.setCart(cart);
		userRepo.save(user);
		return ServiceResponse.builder().data(user.getCart()).build();
	}

}
