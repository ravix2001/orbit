package com.ravi.orbit.entity;

import com.ravi.orbit.enums.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String description;

    private double discount;

    private LocalDateTime validityStartDate;

    private LocalDateTime validityExpiryDate;

    private double minimumOrderAmount;

    private double maximumOrderAmount;

    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "product_id", insertable = false, updatable = false)
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

}
