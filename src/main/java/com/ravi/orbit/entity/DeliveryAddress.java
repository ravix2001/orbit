package com.ravi.orbit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "delivery_address")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String landmark;

    private String street;

    private String tole;

    private String city;

    private String state;

    private String country;

    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

}
