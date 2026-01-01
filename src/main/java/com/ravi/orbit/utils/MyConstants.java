package com.ravi.orbit.utils;

public class MyConstants {

    public static final String ERR_MSG_NOT_FOUND = "Not found: ";
    public static final String ERR_MSG_BAD_REQUEST = "Invalid: ";
    public static final String ERR_MSG_EMPTY = "Empty: ";
    public static final String ERR_MSG_ALREADY_EXIST = "Already Exist: ";

    public static final String RE_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$";

    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 15;            // 15 minutes
    public static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7 days

}
