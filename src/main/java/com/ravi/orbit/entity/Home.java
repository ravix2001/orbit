package com.ravi.orbit.entity;

import lombok.Data;

import java.util.List;

@Data
public class Home {

    private List<HomeCategory> electricCategories;

    private List<HomeCategory> shopCategories;

    private List<HomeCategory> dealCategories;

    private List<HomeCategory> grid;

    private List<Deal> deals;

}
