package com.ecommerce.shopping.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * Created on 2/14/2017.
 */
@Data
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;


}
