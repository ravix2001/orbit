package com.ravi.orbit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ravi.orbit.enums.EGender;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerDTO {

    public SellerDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SellerDTO(Long id, String firstName, String middleName, String lastName, String phone, String email, String username,
                     EGender gender, LocalDate dob, ERole role, EStatus status, String imgURL,
                     String address, String zipcode, String state, String countryCode, String citizenNumber, String nid, String pan) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.dob = dob;
        this.role = role;
        this.status = status;
        this.imgURL = imgURL;
        this.address = address;
        this.zipcode = zipcode;
        this.state = state;
        this.countryCode = countryCode;
        this.citizenNumber = citizenNumber;
        this.nid = nid;
        this.pan = pan;
    }

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String phone;

    private String email;

    private String username;

    private String password;

    private EGender gender;

    private LocalDate dob;

    private ERole role;

    private EStatus status;

    private String imgURL;

    // address
    private String address;

    private String zipcode;

    private String state;

    private String countryCode;

    private String citizenNumber;

    private String nid;

    private String pan;

}
