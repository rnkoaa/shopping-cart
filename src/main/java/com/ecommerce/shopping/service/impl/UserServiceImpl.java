package com.ecommerce.shopping.service.impl;

import com.ecommerce.shopping.domain.User;
import com.ecommerce.shopping.repository.UserRepository;
import com.ecommerce.shopping.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created using Intellij IDE
 * Created by rnkoaa on 2/14/17.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findOne(Long id) {
        User user = userRepository.findOne(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        User user = userRepository.findByUsername(username);
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
