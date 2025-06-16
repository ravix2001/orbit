package com.ravi.orbit.entity;

import com.ravi.orbit.enums.PAYMENT_METHOD;
import com.ravi.orbit.enums.PAYMENT_ORDER_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PAYMENT_METHOD paymentMethod;

    @Enumerated(EnumType.STRING)
    private PAYMENT_ORDER_STATUS status = PAYMENT_ORDER_STATUS.PENDING;

    private String paymentLinkId;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<Order> orders =new HashSet<>();

}
