package com.ravi.orbit.enums;

public enum ACCOUNT_STATUS {

    PENDING_VERIFICATION,        // Account is created but not verified
    ACTIVE,         // Account is active and in good standing
    SUSPENDED,      // Account is temporarily suspended due to violation
    BANNED,         // Account is permanently banned due to severe violation
    DEACTIVATED,    // Account is temporarily deactivated by the user
    CLOSED          // Account is permanently closed by the user
}
