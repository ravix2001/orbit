package com.ravi.orbit.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    // Constructor with message
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    // Constructor with message and cause (optional)
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}