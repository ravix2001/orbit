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

    public ProductDTO(Long id, String code, String name, String brand, String features, String description,
                      EStatus status, Integer quantity, Double marketPrice, Double discountPercent,
                      Double discountAmount, Double sellingPrice, String imageUrl) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.features = features;
        this.description = description;
        this.status = status;
        this.quantity = quantity;
        this.marketPrice = marketPrice;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.sellingPrice = sellingPrice;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Long id, String name, String brand, String features, String description,
                      Double marketPrice, Double discountPercent, Double discountAmount,
                      Double sellingPrice, String imageUrl, Long categoryId) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.features = features;
        this.description = description;
        this.marketPrice = marketPrice;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.sellingPrice = sellingPrice;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    private Long id;
    private String code;
    private String name;
    private String brand;
    private String features;
    private String description;
    private EStatus status;
    private Integer quantity;
    private Double marketPrice;
    private Double discountPercent;
    private Double discountAmount;
    private Double sellingPrice;
    private List<ColorDTO> colors;
    private List<SizeDTO> sizes;
    private String imageUrl;

    private Long categoryId;
    private Long sellerId;

}
