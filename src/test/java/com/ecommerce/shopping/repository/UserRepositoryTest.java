package com.ecommerce.shopping.repository;


import com.ecommerce.shopping.domain.Cart;
import com.ecommerce.shopping.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 2/14/2017.
 */
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class UserRepositoryTest {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testContextLoads() {
        assertThat(cartRepository).isNotNull();
    }


    @Test
    public void givenUserCartCanBeFound() {
        User user = User.builder().firstName("Richard")
                        .lastName("Amoako Agyei")
                        .username("richard")
                        .build();

        Cart cart = Cart.builder().user(user).build();
        user.setCart(cart);

        //When the user has not been saved, this will result in null id's
        assertThat(user.getId()).isNull();
        assertThat(cart.getId()).isNull();

        user = userRepository.save(user);

        //then the id's will have been assigned.
        assertThat(user).isNotNull();
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getCart()).isNotNull();
        assertThat(user.getCart().getId()).isGreaterThan(0L);
    }

    @Test
    public void whenUserIsDeletedCartIsAlsoDeleted() {
        User user = User.builder().firstName("Richard")
                        .lastName("Amoako Agyei")
                        .username("richard")
                        .build();

        Cart cart = Cart.builder().user(user).build();
        user.setCart(cart);

        //When the user has not been saved, this will result in null id's
        assertThat(user.getId()).isNull();
        assertThat(cart.getId()).isNull();

        user = userRepository.save(user);

        //then the id's will have been assigned.
        assertThat(user).isNotNull();
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getCart()).isNotNull();
        assertThat(user.getCart().getId()).isGreaterThan(0L);

        userRepository.delete(user);

        final Cart byUser = cartRepository.findByUser(user);
        assertThat(byUser).isNull();

    }
}