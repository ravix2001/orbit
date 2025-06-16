package com.ravi.orbit.dto;

import com.ravi.orbit.entity.Address;
import com.ravi.orbit.entity.BankDetails;
import com.ravi.orbit.entity.BusinessDetails;
import com.ravi.orbit.enums.ACCOUNT_STATUS;
import lombok.Data;

@Data
public class SellerDto {

    private String fullName;

    private String username;

    private String password;

    private String email;

    private String phone;

    private BusinessDetails businessDetails;

    private BankDetails bankDetails;

    private String pan;     // PAN => Personal Account Number

    private Address pickupAddress;

}
