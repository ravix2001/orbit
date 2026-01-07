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

    public ProductDTO(Long id, String code, String name, String brand, EStatus status, String features, String description,
                      Integer quantity, Double marketPrice, Double discountPercent, Double discountAmount,
                      Double sellingPrice, Long categoryId, String categoryName, Long userId, String imageUrl) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.status = status;
        this.features = features;
        this.description = description;
        this.quantity = quantity;
        this.marketPrice = marketPrice;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.sellingPrice = sellingPrice;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.sellerId = userId;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Long id, String name, String brand, String description, Integer quantity,
                      Double marketPrice, Double discountPercent, Double discountAmount,
                      Double sellingPrice, Long categoryId, String categoryName, Long userId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.quantity = quantity;
        this.marketPrice = marketPrice;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.sellingPrice = sellingPrice;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.sellerId = userId;
        this.imageUrl = imageUrl;
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

    private String categoryName;
    private String sellerName;

}
