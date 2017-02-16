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
@ToString(exclude = {"cart"})
@EqualsAndHashCode(exclude = {"cart", "addresses"})
@Getter
@Setter
@Builder
public class User {

    @Tolerate
    User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private String username;

    private String firstName;
    private String lastName;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Address> addresses = Sets.newHashSet();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<PaymentMethod> paymentMethods = Sets.newHashSet();


    public void addPaymentMethod(PaymentMethod paymentMethod){
        if (paymentMethods == null)
            paymentMethods = Sets.newHashSet();

        paymentMethod.setUser(this);
        paymentMethods.add(paymentMethod);
    }

    public void addAddress(Address address) {
        if (addresses == null)
            addresses = Sets.newHashSet();

        address.setUser(this);
        addresses.add(address);
    }
}
