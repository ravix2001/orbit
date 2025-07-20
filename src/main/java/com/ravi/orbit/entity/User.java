package com.ravi.orbit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ravi.orbit.enums.ACCOUNT_STATUS;
import com.ravi.orbit.enums.GENDER;
import com.ravi.orbit.enums.USER_ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(exclude = {"addresses", "usedCoupons"})
@EqualsAndHashCode(exclude = {"usedCoupons"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address = new Address();

    @Enumerated(EnumType.STRING)
    private GENDER gender;

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_USER;

    @Enumerated(EnumType.STRING)
    private ACCOUNT_STATUS accountStatus = ACCOUNT_STATUS.PENDING_VERIFICATION;

    @ManyToMany()
    @JsonIgnore
    private Set<Coupon> usedCoupons = new HashSet<>();

}
