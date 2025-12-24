//package com.ravi.orbit.entity;
//
//import com.ravi.orbit.enums.EPaymentMethod;
//import com.ravi.orbit.enums.EPaymentOrderStatus;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "payment_order")
//public class PaymentOrder {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private double amount;
//
//    @Enumerated(EnumType.STRING)
//    private EPaymentMethod paymentMethod;
//
//    @Enumerated(EnumType.STRING)
//    private EPaymentOrderStatus status = EPaymentOrderStatus.PENDING;
//
//    private String paymentLinkId;
//
//    @ManyToOne
//    private User user;
//
//    @OneToMany
//    private Set<Order> orders =new HashSet<>();
//
//}
