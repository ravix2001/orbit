package com.ravi.orbit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Data   // caused circular references due to toString present in @Data annotation
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cart"})
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne                  // many carts can have the same product
    private Product product;

    private String size;

    private int quantity = 1;

    private double marketPrice;

    private double sellingPrice;

    private Long userId;

}
