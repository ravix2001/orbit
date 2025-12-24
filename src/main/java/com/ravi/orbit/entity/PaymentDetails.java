//package com.ravi.orbit.entity;
//
//import com.ravi.orbit.enums.PAYMENT_STATUS;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
////@Embeddable
//@Data
//public class PaymentDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String paymentId;
//
//    @Column(nullable = false, unique = true)
//    private String transactionId;
//
//    @Column(nullable = false)
//    private double amount;
//
//    private LocalDateTime createdAt =  LocalDateTime.now();
//
//    private LocalDateTime updatedAt;
//
//    private String paymentGateway;
//
//    private String referenceId;
//
//    @Enumerated(EnumType.STRING)            // enum values should be saved as strings in the database rather than as their ordinal positions (integers).
//    private PAYMENT_STATUS paymentStatus = PAYMENT_STATUS.PENDING;
//
//}
