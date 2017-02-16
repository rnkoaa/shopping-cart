package com.ecommerce.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created on 2/14/2017.
 */
@Entity
@Builder
@Getter
@Setter
@ToString(exclude = {"user", "items"})
@EqualsAndHashCode(exclude = "items")
public class Cart {

    @Tolerate
    Cart(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @JsonIgnore
    @OneToOne
    @Getter
    @Setter
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<CartItem> items = Sets.newHashSet();

    public void addItem(CartItem cartItem) {
        if (items == null)
            items = Sets.newHashSet();

        items.add(cartItem);
    }


    public Optional<CartItem> findItem(Product product) {

        if (items == null)
            return Optional.empty();
        return getItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getProduct() != null
                                && Objects.equals(cartItem.getProduct().getId(), product.getId()))
                .findFirst();
    }

    public void removeItem(CartItem removeItem) {
        if (items != null) {
            items.removeIf(cartItem -> Objects.equals(cartItem.getId(), removeItem.getId()));
        }
    }

    public void updateItem(CartItem cartItem) {
        removeItem(cartItem);
        addItem(cartItem);
    }

    public boolean isEmpty() {
        return items != null && items.size() == 0;
    }
}
