package com.ecommerce.shopping.service.impl;

import com.ecommerce.shopping.domain.Product;
import com.ecommerce.shopping.repository.ProductRepository;
import com.ecommerce.shopping.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findBySerial(String serialNumber) {
        Product product = productRepository.findBySerialNumber(serialNumber);
        return Optional.ofNullable(product);
    }

    @Override
    public Optional<Product> findOne(Long productId) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
