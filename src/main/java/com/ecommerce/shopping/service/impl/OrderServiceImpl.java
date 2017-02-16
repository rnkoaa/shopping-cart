package com.ecommerce.shopping.service.impl;

import com.ecommerce.shopping.domain.*;
import com.ecommerce.shopping.repository.OrderRepository;
import com.ecommerce.shopping.service.OrderService;
import com.ecommerce.shopping.service.UserService;
import com.ecommerce.shopping.util.CartEmptyException;
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
}