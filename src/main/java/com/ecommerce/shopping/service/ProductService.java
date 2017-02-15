package com.ecommerce.shopping.service;

import com.ecommerce.shopping.domain.Product;

import java.util.Optional;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
public interface ProductService {
    Optional<Product> findBySerial(String serialNumber);

    Optional<Product> findOne(Long productId);
}
