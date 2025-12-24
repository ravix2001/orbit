package com.ravi.orbit.utils;

import com.ravi.orbit.exceptions.BadRequestException;

public class CommonValidator {

    public static void validatePhoneNo(String phoneNumber) {
        String strippedPhoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        if (CommonMethods.isEmpty(phoneNumber) ||
                strippedPhoneNumber.length() < 8 || strippedPhoneNumber.length() > 15) {
            throw new BadRequestException(MyConstants.ERR_MSG_BAD_REQUEST + "Phone");
        }
    }

    public static void validateEmail(String email) {
        if (CommonMethods.isEmpty(email)) {
            throw new BadRequestException("Email cannot be empty");
        }
        if (!email.matches(MyConstants.RE_EMAIL)) {
            throw new BadRequestException(MyConstants.ERR_MSG_BAD_REQUEST + "Email");
        }
    }


}
