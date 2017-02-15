package com.ecommerce.shopping.service;

import com.ecommerce.shopping.domain.User;

import java.util.Optional;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
public interface UserService {
    Optional<User> findOne(Long id);

    Optional<User> findByUserName(String username);
}
