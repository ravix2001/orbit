package com.ravi.orbit.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private String productId;
    private String name;
    private String brand;
    private String description;
    private List<String> images;
    private Integer quantity;      // changed from int to Integer
    private String color;
    private String size;
    private Double marketPrice;    // changed from double to Double
    private Double discount;       // changed from double to Double
    private Double sellingPrice;   // changed from double to Double
    private List<Long> categoryIds;
    private Long sellerId;    // reference to existing seller

}
