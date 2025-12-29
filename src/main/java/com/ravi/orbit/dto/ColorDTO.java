package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorDTO {

    public ColorDTO(Long id, String color, boolean isAvailable, Double price, Integer quantity, Long productId) {
        this.id = id;
        this.color = color;
        this.isAvailable = isAvailable;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    private Long id;

    private String color;

    private boolean isAvailable;

    private Double price;

    private Integer quantity;

    private Long productId;

}
