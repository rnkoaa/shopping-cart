package com.ecommerce.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created on 2/14/2017.
 */
@Entity
@Data
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> items = Sets.newHashSet();

    public void addItem(CartItem cartItem) {
        items.add(cartItem);
    }
}
