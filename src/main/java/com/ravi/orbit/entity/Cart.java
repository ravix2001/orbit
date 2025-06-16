package com.ravi.orbit.entity;

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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)      // the operations performed on the **parent entity** (e.g., `persist`, `remove`, etc.) **will be automatically applied to its associated child entities**.
    private Set<CartItem> cartItems = new HashSet<>();

    private int totalItems;

    private double totalMarketPrice;

    private String code;

    private double discount;

    private double totalSellingPrice;

}
