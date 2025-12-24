package com.ravi.orbit.dto;

import lombok.Data;

@Data
public class ReviewDTO {

    private Long id;
    private String reviewerName;
    private String reviewText;
    private int rating;
    private String createdAt;

}
