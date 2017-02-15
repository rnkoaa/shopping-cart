package com.ecommerce.shopping.repository;

import com.ecommerce.shopping.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2/14/2017.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySerialNumber(String serialNumber);
}
