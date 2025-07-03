package com.ravi.orbit.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDto {

    private Long userId;
    private Long productId;
    private int quantity = 1;
}
