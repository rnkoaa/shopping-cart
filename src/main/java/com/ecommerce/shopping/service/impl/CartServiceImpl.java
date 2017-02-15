package com.ecommerce.shopping.service.impl;

import com.ecommerce.shopping.domain.Cart;
import com.ecommerce.shopping.domain.CartItem;
import com.ecommerce.shopping.domain.Product;
import com.ecommerce.shopping.domain.User;
import com.ecommerce.shopping.repository.CartRepository;
import com.ecommerce.shopping.service.CartService;
import com.ecommerce.shopping.service.ProductService;
import com.ecommerce.shopping.service.UserService;
import com.ecommerce.shopping.util.ProductNotFoundException;
import com.ecommerce.shopping.util.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    private final UserService userService;

    private final CartRepository cartRepository;

    private final ProductService productService;

    public CartServiceImpl(UserService userService, CartRepository cartRepository, ProductService productService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional
    @Override
    public Cart addItemToCart(User user, Product product) {
        if (user.getId() == null) {
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            user = optionalUser
                    .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        }

        if (product.getId() == null) {
            Optional<Product> optionalProduct = productService.findBySerial(product.getSerialNumber());
            product = optionalProduct
                    .orElseThrow(() -> new ProductNotFoundException("There is no product with the" +
                            "supplied serial number"));
        }

        Cart cart = cartRepository.findByUser(user);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .unitPrice(new BigDecimal(4.90))
                .build();
        cart.addItem(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Cart addItemToCart(User user, Long productId) {
        if (user.getId() == null) {
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            user = optionalUser
                    .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        }

        Optional<Product> optionalProduct = productService.findOne(productId);
        Product product = optionalProduct
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the" +
                        "supplied serial number"));

        Cart cart = cartRepository.findByUser(user);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .unitPrice(new BigDecimal(4.90))
                .build();
        cart.addItem(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Cart addItemToCart(Long userId, Long productId) {
        Optional<User> optionalUser = userService.findOne(userId);
        User user = optionalUser
                .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        Optional<Product> optionalProduct = productService.findOne(productId);
        Product product = optionalProduct
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the" +
                        "supplied serial number"));

        Cart cart = cartRepository.findByUser(user);

        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(1)
                .unitPrice(new BigDecimal(4.90))
                .build();
        cart.addItem(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(Long userId, Long productId) {
        return null;
    }

    @Override
    public Cart clearCart(User user) {
        if (user.getId() == null) {
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            user = optionalUser
                    .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        }

        Cart cart = cartRepository.findByUser(user);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
