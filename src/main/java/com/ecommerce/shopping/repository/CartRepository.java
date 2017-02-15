package com.ecommerce.shopping.repository;

import com.ecommerce.shopping.domain.Cart;
import com.ecommerce.shopping.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 2/14/2017.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
