package com.ecommerce.shopping.repository;

import com.ecommerce.shopping.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 2/16/2017.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
