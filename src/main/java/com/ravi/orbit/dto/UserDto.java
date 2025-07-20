package com.ravi.orbit.dto;

import com.ravi.orbit.entity.Address;
import com.ravi.orbit.enums.GENDER;
import com.ravi.orbit.enums.USER_ROLE;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private String fullName;
    private String email;
    private String phone;
    private Address address;
    private GENDER gender;
    private String username;
    private String password;

}
