package com.ecommerce.shopping.domain;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2/14/2017.
 */
@Entity
@Data
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne( fetch = FetchType.EAGER)
    private Address billingAddress;

    @OneToOne(fetch = FetchType.EAGER)
    private Address shippingAddress;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> items = Sets.newHashSet();

    private BigDecimal subTotal;
}
