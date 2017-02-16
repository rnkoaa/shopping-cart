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
    public Address(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(name = "street_2")
    private String street2;

    private String apartment;

    private String city;

    @Column(length = 2)
    private String stateCode;

    private String state;

    private String zipCode;

    @Column(name = "is_primary")
    private boolean primary = false;

    @ManyToOne
    private User user;
}
