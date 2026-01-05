package com.ravi.orbit.enums;

public enum EStatus {

    PENDING,        // Account is created but not verified
    ACTIVE,         // Account is active and in good standing
    INACTIVE,       // Account is temporarily deactivated by the user
    DELETED,        // Account is deleted (soft delete)
    SUSPENDED,      // Account is temporarily suspended due to violation

}
