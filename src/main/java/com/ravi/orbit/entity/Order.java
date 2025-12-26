package com.ravi.orbit.entity;

import com.ravi.orbit.enums.EOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private String status;

    @ManyToOne
    private DeliveryAddress shippingAddress;

    private int totalItems;

    private double totalMarketPrice;

    private double totalDiscount;

    private double totalSellingPrice;

    @Enumerated(EnumType.STRING)
    private EOrderStatus orderStatus;

//    @Embedded
//    private PaymentDetails paymentDetails = new PaymentDetails();

    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime deliveryDate = orderDate.plusDays(3);     // delivery date = 3 days of order date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    @Column(name = "seller_id", insertable = false, updatable = false)
    private Long sellerId;

}
