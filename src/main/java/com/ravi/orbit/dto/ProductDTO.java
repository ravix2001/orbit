package com.ravi.orbit.dto;

import com.ravi.orbit.enums.EStatus;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    public ProductDTO(Long id, String productId, String name, String brand, String description,
                      EStatus status, Integer quantity, Double marketPrice, Double discountPercent,
                      Double sellingPrice, String imgUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.status = status;
        this.quantity = quantity;
        this.marketPrice = marketPrice;
        this.discountPercent = discountPercent;
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

    private Long sellerId;

}
