package com.ravi.orbit.dto;

import lombok.Data;

@Data
public class CategoryDto {

    private String name;
    private Long parentCategoryId; // Optional if it's a root category
    private int level;

}
