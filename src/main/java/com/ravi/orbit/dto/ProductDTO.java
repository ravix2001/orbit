package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    public ProductDTO(Long id, String productId, String name, String brand, String description,
                      EStatus status, Integer quantity, Double marketPrice, Double discountPercent,
                      Double discountAmount, Double sellingPrice, String imgUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.status = status;
        this.quantity = quantity;
        this.marketPrice = marketPrice;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.sellingPrice = sellingPrice;
        this.imgUrl = imgUrl;
    }

    private Long id;
    private String productId;
    private String name;
    private String brand;
    private String description;
    private EStatus status;
    private Integer quantity;
    private Double marketPrice;
    private Double discountPercent;
    private Double discountAmount;
    private Double sellingPrice;
    private List<ColorDTO> colors;
    private List<SizeDTO> sizes;
    private String imgUrl;

    private Long categoryId;
    private Long sellerId;

}
