package com.ecommerce.shopping.util;

/**
 * Created on 2/16/2017.
 */
public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String message) {
        super(message);
    }
}
