package com.ecommerce.shopping.util;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
