package com.ravi.orbit.utils;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.UserDTO;

public class Validator {

    public static void validateUserSignup(UserDTO request) {
        CommonValidator.validatePhoneNo(request.getPhone());
        CommonValidator.validateEmail(request.getEmail());
    }

    public static void validateSellerSignup(SellerDTO request) {
        CommonValidator.validatePhoneNo(request.getPhone());
        CommonValidator.validateEmail(request.getEmail());
    }

}
