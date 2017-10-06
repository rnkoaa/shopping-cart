package com.ecommerce.shopping.service.impl;

import com.ecommerce.shopping.domain.*;
import com.ecommerce.shopping.repository.OrderRepository;
import com.ecommerce.shopping.service.OrderService;
import com.ecommerce.shopping.service.UserService;
import com.ecommerce.shopping.util.CartEmptyException;
import com.ecommerce.shopping.util.InvalidOrderOperation;
import com.ecommerce.shopping.util.OrderNotFoundException;
import com.ecommerce.shopping.util.UserNotFoundException;
import com.ecommerce.shopping.util.strings.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ecommerce.shopping.domain.OrderStatus.*;

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

        Order order = Order.builder().user(user).orderStatus(NEW).build();

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
        if (order.getOrderStatus() == CANCELLED || order.getOrderStatus() == SHIPPED) {
            throw new InvalidOrderOperation("Payment cannot be applied to an order that is currently canceled or shipped.");
        }

        final Payment payment = Payment.builder().paymentMethod(paymentMethod).amount(order.getSubTotal()).order(order).build();
        order.addPayment(payment);

        order.setOrderStatus(PAID);
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
        if (order.getId() == null || order.getId() == 0L) {
            throw new OrderNotFoundException("The order is not known");
        }

        final OrderStatus orderStatus = order.getOrderStatus();
        if (orderStatus == COMPLETED || orderStatus == SHIPPED) {
            throw new InvalidOrderOperation("The order is already completed or shipped, cannot be cancel an already completed order");
        }

        //TODO
        //Alert any payment service to refund any payment
        //remove items and make them available for orders by other customers.
        //notify user of cancelled order
        order.setOrderStatus(CANCELLED);
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
        if (order.getId() == null || order.getId() == 0L) {
            throw new OrderNotFoundException("The order is not known");
        }

        final OrderStatus orderStatus = order.getOrderStatus();
        if (orderStatus == CANCELLED || orderStatus == SHIPPED) {
            throw new InvalidOrderOperation("The order is already cancelled, cannot be completed or Shipped");
        }

        if ((orderStatus != PAID)) {
            throw new InvalidOrderOperation("Cannot complete an order that is not paid");
        }

        //TODO
        //remove items from ordering,
        //decrement the counts so that the inventory is acurate based on the current order
        //notify user of complete order
        order.setOrderStatus(COMPLETED);
        return orderRepository.save(order);
    }

    @Override
    public Order complete(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.orElseThrow(() -> new OrderNotFoundException("There is no order for this Supplied Id."));
        return complete(order);
    }
}
