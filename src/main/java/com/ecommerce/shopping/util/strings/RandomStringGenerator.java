package com.ecommerce.shopping.util.strings;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created on 2/16/2017.
 */
public class RandomStringGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32).toUpperCase();
    }
}
