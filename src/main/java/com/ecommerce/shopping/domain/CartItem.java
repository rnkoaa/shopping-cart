package com.ecommerce.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created on 2/14/2017.
 */
@Entity
@Builder
@Setter
@Getter
@ToString(exclude = {"product"})
@EqualsAndHashCode(exclude = {"cart"})
public class CartItem {

    @Tolerate
    CartItem() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Getter
    @Setter
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private BigDecimal unitPrice;

    @Getter
    @Setter
    private boolean inStock = true;
}
