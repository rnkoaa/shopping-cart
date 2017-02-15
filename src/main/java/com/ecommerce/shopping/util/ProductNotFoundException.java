package com.ecommerce.shopping.util;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
