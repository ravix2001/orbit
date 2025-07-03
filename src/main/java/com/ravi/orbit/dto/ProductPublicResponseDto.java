package com.ravi.orbit.dto;

import com.ravi.orbit.entity.Review;
import lombok.Data;

import java.util.List;

@Data
public class ProductPublicResponseDto {

    private Long id;
    private String productId;
    private String name;
    private String brand;
    private String description;
    private List<String> images;
    private int quantity;
    private String color;
    private String size;
    private double marketPrice;
    private double discount;
    private double sellingPrice;
    private int numRatings;
    private double avgRating;
    private String categoryName;
    private SellerPublicDto seller;
//    private String sellerName;
    private String createdAt;
    private List<Review> reviews;

}
