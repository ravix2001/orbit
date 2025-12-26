package com.ravi.orbit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "seller_report")
public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int totalOrders = 0;

    private int cancelledOrders = 0;

    private int totalTransactions = 0;

    private double totalSales = 0;

    private double totalRefunds = 0;

    private double totalRevenue = 0;

    private double totalTax = 0;

    private double netRevenue = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    @Column(name = "seller_id", insertable = false, updatable = false)
    private Long sellerId;

}
