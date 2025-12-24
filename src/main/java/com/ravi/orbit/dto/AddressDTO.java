package com.ravi.orbit.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String name;
    private String locality;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String phone;
}
