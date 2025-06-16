package com.ravi.orbit.entity;

import com.ravi.orbit.enums.ORDER_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    @ManyToOne
    private User user;

    private Long sellerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private String status;

    @ManyToOne
    private Address shippingAddress;

    private int totalItems;

    private double totalMarketPrice;

    private double totalDiscount;

    private double totalSellingPrice;

    @Enumerated(EnumType.STRING)
    private ORDER_STATUS orderStatus;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime deliveryDate = orderDate.plusDays(3);     // delivery date = 3 days of order date

}
