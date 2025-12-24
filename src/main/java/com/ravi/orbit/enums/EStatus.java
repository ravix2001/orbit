package com.ravi.orbit.enums;

public enum EStatus {

    PENDING,        // Account is created but not verified
    ACTIVE,         // Account is active and in good standing
    INACTIVE,       // Account is temporarily deactivated by the user
    DELETED,        // Account is deleted (soft delete)
    CLOSED,         // Account is permanently closed by the user
    SUSPENDED,      // Account is temporarily suspended due to violation
    BANNED          // Account is permanently banned due to severe violation

}
