package com.caffeinated.cartexpressoservice.service;

import com.caffeinated.cartexpressoservice.model.order.CreateOrderRequest;
import com.caffeinated.cartexpressoservice.model.order.OrderConfirmationRequest;
import com.caffeinated.cartexpressoservice.model.ServiceResponse;
import com.razorpay.RazorpayException;

public interface IOrderService {
    ServiceResponse createOrder(CreateOrderRequest orderRequest) throws RazorpayException;

    ServiceResponse orderConfirmation(OrderConfirmationRequest orderRequest, Integer userId);

    ServiceResponse getOrderDetail(Integer userId);
}
