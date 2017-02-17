package com.ecommerce.shopping.service.impl;

import com.ecommerce.shopping.domain.*;
import com.ecommerce.shopping.repository.OrderRepository;
import com.ecommerce.shopping.service.OrderService;
import com.ecommerce.shopping.service.UserService;
import com.ecommerce.shopping.util.CartEmptyException;
import com.ecommerce.shopping.util.OrderNotFoundException;
import com.ecommerce.shopping.util.UserNotFoundException;
import com.ecommerce.shopping.util.strings.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 2/16/2017.
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(User user) {
        if (user.getId() == null) {
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            user = optionalUser
                    .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        }

        Cart cart = user.getCart();
        if (cart.isEmpty())
            throw new CartEmptyException("Cannot Create an order with an empty cart");

        Order order = Order.builder().user(user).build();

        final Set<CartItem> items = cart.getItems();
        final Set<OrderItem> orderItems = items
                .stream()
                .map(cartItem -> OrderItem.builder()
                                          .order(order)
                                          .product(cartItem.getProduct())
                                          .quantity(cartItem.getQuantity())
                                          .unitPrice(cartItem.getUnitPrice())
                                          .build())
                .collect(Collectors.toSet());
        order.setItems(orderItems);

        //assign a unique order confirmation key
        order.setOrderKey(RandomStringGenerator.nextSessionId());


        return orderRepository.save(order);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order applyPayment(Order order, PaymentMethod paymentMethod) {
        final Payment payment = Payment.builder().paymentMethod(paymentMethod).amount(order.getSubTotal()).order(order).build();
        order.addPayment(payment);

        return orderRepository.save(order);
    }

    @Override
    public Order applyPayment(Long orderId, PaymentMethod paymentMethod) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("There is no order for this Supplied Id."));
        return applyPayment(order, paymentMethod);
    }

    @Override
    public Order cancel(Order order) {
        // if(order.getOrderStatus() != OrderStatus.PAID || order.getOrderStatus() )
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order cancel(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("There is no order for this Supplied Id."));
        return cancel(order);
    }

    @Override
    public Order complete(Order order) {
        return null;
    }

    @Override
    public Order complete(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("There is no order for this Supplied Id."));
        return complete(order);
    }
}
