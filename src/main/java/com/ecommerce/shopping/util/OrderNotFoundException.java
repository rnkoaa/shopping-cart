package com.ecommerce.shopping.util;

/**
 * Created on 2/17/2017.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
