package com.ecommerce.shopping.service;

import com.ecommerce.shopping.domain.Order;
import com.ecommerce.shopping.domain.PaymentMethod;
import com.ecommerce.shopping.domain.User;

/**
 * Created on 2/16/2017.
 */
public interface OrderService {
    Order createOrder(User user);

    Order save(Order order);

    Order applyPayment(Order order, PaymentMethod paymentMethod);

    Order cancel(Order order);
}