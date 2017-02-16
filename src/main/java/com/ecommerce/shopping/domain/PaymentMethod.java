package com.ecommerce.shopping.domain;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

/**
 * Created on 2/16/2017.
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@Builder
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String name;

    @Tolerate
    public PaymentMethod() {
    }

    @ManyToOne
    @Getter
    @Setter
    private User user;

}
