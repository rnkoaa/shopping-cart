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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    public CartServiceImpl(UserService userService, CartRepository cartRepository, ProductService productService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Transactional
    @Override
    public Cart addItemToCart(User user, Product product) {
        return addItem(user, product, 1);
    }

    @Override
    public Cart addItemToCart(User user, Product product, int quantity) {
        return addItem(user, product, quantity);
    }


    @Transactional
    @Override
    public Cart addItemToCart(User user, Long productId) {
        if (user.getId() == null) {
            LOGGER.info("UserId is not set for the user? check to see if the user is valid");
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            user = optionalUser
                    .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        }

        Optional<Product> optionalProduct = productService.findOne(productId);
        Product product = optionalProduct
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the" +
                        "supplied serial number"));

        return updateCart(user, product, 1);
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

        return updateCart(user, product, 1);
    }

    @Override
    public Cart removeItem(Long userId, Long productId) {
        Optional<User> optionalUser = userService.findOne(userId);
        User user = optionalUser
                .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));

        Optional<Product> optionalProduct = productService.findOne(productId);
        Product product = optionalProduct
                .orElseThrow(() -> new ProductNotFoundException("There is no product with the" +
                        "supplied serial number"));

        return removeItemFromCart(user, product);
    }

    @Override
    public Cart removeItem(User user, Product product) {

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

        return removeItemFromCart(user, product);
    }

    @Override
    public Cart clear(User user) {
        if (user.getId() == null) {
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            user = optionalUser
                    .orElseThrow(() -> new UserNotFoundException("There is no user with this username"));
        }

        final User cartUser = user;
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        return optionalCart
                .map(cart -> {
                    cart.getItems().clear();
                    return cart;
                }).orElseGet(() -> {
                    Cart cart = Cart.builder().user(cartUser).build();
                    return cartRepository.save(cart);
                });

    }

    @Override
    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Optional<Cart> findByUsername(String username) {
        Optional<User> optionalUser = userService.findByUserName(username);
        return optionalUser.flatMap(user -> Optional.of(user.getCart()));
    }

    @Override
    public Optional<Cart> findByUserId(Long id) {
        Optional<User> optionalUser = userService.findOne(id);
        return optionalUser.flatMap(user -> Optional.of(user.getCart()));
    }

    @Override
    public Cart updateItem(User user, Product product, int itemCount) {
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
        return updateCart(user, product, itemCount);
    }

    private Cart addItem(User user, Product product, int quantity) {
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

        return updateCart(user, product, quantity);
    }

    private Cart removeItemFromCart(User user, Product product) {
        final User cartUser = user;
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        return optionalCart.map(cart -> {
            Optional<CartItem> item = cart.findItem(product);
            item.ifPresent(cart::removeItem);
            return cartRepository.save(cart);
        }).orElseGet(() -> {
            Cart cart = Cart.builder().user(cartUser).build();
            return cartRepository.save(cart);
        });
    }

    private Cart updateCart(User user, Product product, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findByUser(user);

        final Cart mCart = optionalCart.map(cart -> {
            Optional<CartItem> optionalItem = cart.findItem(product);
            CartItem updatedCartItem = optionalItem.map(cartItem -> {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return cartItem;
            }).orElse(CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(4.90))
                    .build());

            cart.updateItem(updatedCartItem);
            cart.setUser(user);
            user.setCart(cart);

            return cart;
        }).orElse(Cart.builder().user(user).build());

        return cartRepository.save(mCart);
    }
}
