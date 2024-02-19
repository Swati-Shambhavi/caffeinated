package com.caffeinated.cartexpressoservice.service.impl;

import com.caffeinated.cartexpressoservice.config.RazorPayConfig;
import com.caffeinated.cartexpressoservice.entity.cart.Cart;
import com.caffeinated.cartexpressoservice.entity.cart.CartItem;
import com.caffeinated.cartexpressoservice.entity.order.Order;
import com.caffeinated.cartexpressoservice.entity.order.OrderItem;
import com.caffeinated.cartexpressoservice.entity.order.OrderPayment;
import com.caffeinated.cartexpressoservice.exception.ResourceNotFoundException;
import com.caffeinated.cartexpressoservice.model.*;
import com.caffeinated.cartexpressoservice.model.order.*;
import com.caffeinated.cartexpressoservice.queue.ProductUpdateMessage;
import com.caffeinated.cartexpressoservice.queue.ProductUpdateMessageSender;
import com.caffeinated.cartexpressoservice.repo.CartRepository;
import com.caffeinated.cartexpressoservice.repo.OrderPaymentRepository;
import com.caffeinated.cartexpressoservice.repo.OrderRepository;
import com.caffeinated.cartexpressoservice.service.IOrderService;
import com.caffeinated.cartexpressoservice.util.MapMeUp;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService implements IOrderService {
    private RazorPayConfig razorPayConfig;
    private CartRepository cartRepo;
    private OrderRepository orderRepository;
    private OrderPaymentRepository orderPaymentRepository;
    private ProductUpdateMessageSender productUpdateMessageSender;

    @Override
    public ServiceResponse createOrder(CreateOrderRequest orderRequest) throws RazorpayException {
        RazorpayClient razorpayClient = razorPayConfig.razorpayClient();
        JSONObject options = new JSONObject();
        options.put("amount", orderRequest.getAmount());
        options.put("currency", orderRequest.getCurrency());
        com.razorpay.Order  order = razorpayClient.orders.create(options);
        ServiceResponse response = ServiceResponse.builder().data(CreateOrderResponse.builder().orderId(order.get("id")).apiKey(razorPayConfig.getApiKey()).amount(order.get("amount")).currency(order.get("currency")).build()).build();
        return response;
    }

    @Override
    public ServiceResponse orderConfirmation(OrderConfirmationRequest orderRequest, Integer userId) {
        if (verifyRazorpaySignature(orderRequest.getRazorpayOrderId(), orderRequest.getRazorpayPaymentId(), orderRequest.getRazorpaySignature())) {
            Order savedOrder = removeItemsFromCartAndAddToOrder(userId, orderRequest);
            sendProductStockUpdateMsg(savedOrder);
            return ServiceResponse.builder().data(OrderConfirmationResponse.builder().isOrderConfirmed(true).orderDetails(MapMeUp.toOrderDetails(savedOrder)).build()).build();
        } else {
            return ServiceResponse.builder().data(OrderConfirmationResponse.builder().isOrderConfirmed(false).build()).build();
        }
    }

    @Override
    public ServiceResponse getOrderDetail(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", String.valueOf(userId)));
        return ServiceResponse.builder().data(orders.stream().map(MapMeUp::toOrderDetails)).build();
    }

    private Order removeItemsFromCartAndAddToOrder(Integer userId, OrderConfirmationRequest orderRequest) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", String.valueOf(userId)));
        Order order = Order.builder()
                .userId(userId)
                .totalPrice(cart.getTotalPrice())
                .build();
        order.setOrderItems(mapOrderItems(cart.getCartItems(),order));
        Order savedOrder = orderRepository.save(order);

        OrderPayment orderPayment = OrderPayment.builder()
                .orderId(savedOrder.getId())
                .razorpayOrderId(orderRequest.getRazorpayOrderId())
                .razorpayPaymentId(orderRequest.getRazorpayPaymentId())
                .razorpaySignature(orderRequest.getRazorpaySignature())
                .build();

        orderPaymentRepository.save(orderPayment);

        cart.getCartItems().clear();
        cart.setTotalPrice(0d);
        cartRepo.save(cart);
        return savedOrder;
    }
    private void sendProductStockUpdateMsg(Order savedOrder) {
        for (OrderItem orderItem : savedOrder.getOrderItems()) {
            ProductUpdateMessage updateMessage = new ProductUpdateMessage();
            updateMessage.setProductId(orderItem.getProductId());
            updateMessage.setQuantity(orderItem.getProductQuantity());
            productUpdateMessageSender.sendProductUpdateMessage(updateMessage);
        }
    }

    private List<OrderItem> mapOrderItems(List<CartItem> cartItems, Order order) {
        return cartItems.stream().map(cartItem ->
                {
                    OrderItem orderItem = OrderItem.builder()
                            .productId(cartItem.getProductId())
                            .productName(cartItem.getProductName())
                            .totalPrice(cartItem.getTotalPrice())
                            .productQuantity(cartItem.getProductQuantity())
                            .unitPrice(cartItem.getUnitPrice())
                            .build();
                    order.addOrderItem(orderItem);
                    return orderItem;
                }
        ).collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;

            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(razorPayConfig.getApiSecret().getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] generatedSignature = sha256Hmac.doFinal(payload.getBytes("UTF-8"));

            // Convert the generated signature to hexadecimal
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : generatedSignature) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            String generatedSignatureHex = hexStringBuilder.toString();

            // Directly compare the generated hexadecimal signature with the received signature
            return generatedSignatureHex.equals(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
