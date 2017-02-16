package com.ecommerce.shopping.domain;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Set;

/**
 * Created on 2/14/2017.
 */
@Entity
@Builder
@ToString(exclude = {"cart"})
@EqualsAndHashCode(exclude = {"cart", "addresses"})
@Getter
@Setter
public class User {

    @Tolerate
    User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String firstName;
    private String lastName;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses = Sets.newHashSet();

}
