package com.ravi.orbit.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ravi.orbit.enums.EGender;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role = ERole.SELLER;

    // status
    @Enumerated(EnumType.STRING)
    @Column(name = "seller_status")
    private EStatus status = EStatus.PENDING;

    @Column(name = "img_url")
    private String imgURL;

    // address
    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "state")
    private String state;

    @Column(name = "countryCode")
    private String countryCode;

    @Column(name = "citizen_number")
    private String citizenNumber;   // Citizenship ID Number

    @Column(name = "nid")
    private String nid;     // NID => National Identification Number

    @Column(name = "pan")
    private String pan;     // PAN => Personal Account Number

}
