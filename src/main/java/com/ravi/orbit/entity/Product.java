package com.ravi.orbit.entity;

import com.ravi.orbit.enums.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private EStatus status = EStatus.ACTIVE;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "market_price")
    private double marketPrice;

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "selling_price")
    private double sellingPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    @Column(name = "seller_id", insertable = false, updatable = false)
    private String sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private String categoryId;

}