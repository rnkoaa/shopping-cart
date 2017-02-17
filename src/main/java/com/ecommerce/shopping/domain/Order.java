package com.ecommerce.shopping.domain;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Created on 2/14/2017.
 */
@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = {"items", "subTotal", "payments"})
@ToString
@Table(name = "orders")
public class Order {

    @Tolerate
    public Order() {
    }

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_key", updatable = false, nullable = false, length = 32)
    private String orderKey;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private Address billingAddress;

    @OneToOne(fetch = FetchType.EAGER)
    private Address shippingAddress;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> items = Sets.newHashSet();

    private BigDecimal subTotal;

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    @OneToMany(mappedBy = "order")
    public Set<Payment> payments = Sets.newHashSet();

    public void addPayment(Payment payment) {
        if (payments == null)
            payments = Sets.newHashSet();

        payment.setOrder(this);
        payments.add(payment);

        this.orderStatus = OrderStatus.PAID;
    }
}
