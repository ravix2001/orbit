package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.enums.EStatus;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeDTO {

    public SizeDTO(Long id, String size, boolean isAvailable, Double price, Integer quantity, Long productId) {
        this.id = id;
        this.size = size;
        this.isAvailable = isAvailable;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    private Long id;

    private String size;

    private boolean isAvailable;

    private Double price;

    private Integer quantity;

    private Long productId;

}
