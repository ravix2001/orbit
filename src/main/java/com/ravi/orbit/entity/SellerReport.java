package com.ravi.orbit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Seller seller;

    private int totalOrders = 0;

    private int cancelledOrders = 0;

    private int totalTransactions = 0;

    private double totalSales = 0;

    private double totalRefunds = 0;

    private double totalRevenue = 0;

    private double totalTax = 0;

    private double netRevenue = 0;

}
