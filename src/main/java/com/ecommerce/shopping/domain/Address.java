package com.ecommerce.shopping.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * Created on 2/14/2017.
 */
@Entity
@Data
@Builder
public class Address {

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
