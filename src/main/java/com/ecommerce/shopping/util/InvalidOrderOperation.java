package com.ecommerce.shopping.util;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/18/17.
 */
public class InvalidOrderOperation extends RuntimeException {
    public InvalidOrderOperation(String message) {
        super(message);
    }
}
