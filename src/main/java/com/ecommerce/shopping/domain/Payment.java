package com.ecommerce.shopping.domain;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created on 2/16/2017.
 */
@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Payment {

    @Tolerate
    public Payment(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    private boolean completed = false;

    private LocalDateTime completedTime = LocalDateTime.now();

    @Column(name = "payment_confirmation", length = 32)
    private String confirmation;

    @ManyToOne
    private Address billingAddress;

    @OneToOne
    private Order order;
}
