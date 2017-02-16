package com.ecommerce.shopping.domain;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

/**
 * Created on 2/14/2017.
 */
@Entity
@Builder
@Getter
@Setter
@ToString(exclude = {"user"})
@EqualsAndHashCode
public class Address {

    @Tolerate
    public Address() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String street;

    @Column(name = "street_2")
    @Getter
    @Setter
    private String street2;

    @Getter
    @Setter
    private String apartment;

    @Getter
    @Setter
    private String city;

    @Column(length = 2)
    @Getter
    @Setter
    private String stateCode;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String zipCode;

    @Column(name = "is_primary")
    @Getter
    @Setter
    private boolean primary = false;

    @ManyToOne
    @Getter
    @Setter
    private User user;
}
