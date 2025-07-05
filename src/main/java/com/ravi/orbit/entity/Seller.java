package com.ravi.orbit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ravi.orbit.enums.ACCOUNT_STATUS;
import com.ravi.orbit.enums.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    private String pan;     // PAN => Personal Account Number

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress = new Address();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_SELLER;

//    private boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    private ACCOUNT_STATUS accountStatus = ACCOUNT_STATUS.PENDING_VERIFICATION;

}
