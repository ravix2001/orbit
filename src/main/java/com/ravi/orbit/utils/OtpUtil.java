package com.ravi.orbit.utils;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

public class OtpUtil {

    private static final int OTP_LENGTH = 6;
    private static final int EXPIRATION_TIME_MINUTES = 5;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final ConcurrentHashMap<String, OtpEntry> otpStore = new ConcurrentHashMap<>();

    // Inner class to store OTP and its expiration time
    private static class OtpEntry {
        String otp;
        long expiresAt;

        OtpEntry(String otp, long expiresAt) {
            this.otp = otp;
            this.expiresAt = expiresAt;
        }
    }

    // Generate a numeric OTP of fixed length
    public static String generateOtp(String key) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        long expiresAt = System.currentTimeMillis() + EXPIRATION_TIME_MINUTES * 60 * 1000;
        otpStore.put(key, new OtpEntry(otp.toString(), expiresAt));
        return otp.toString();
    }

    // Validate the OTP
    public static boolean validateOtp(String key, String inputOtp) {
        OtpEntry entry = otpStore.get(key);
        if (entry == null || System.currentTimeMillis() > entry.expiresAt) {
            otpStore.remove(key);
            return false;
        }
        boolean isValid = entry.otp.equals(inputOtp);
        if (isValid) {
            otpStore.remove(key); // OTP is one-time use
        }
        return isValid;
    }

    // Optional: clear expired OTPs (can be scheduled)
    public static void cleanExpiredOtps() {
        long now = System.currentTimeMillis();
        otpStore.entrySet().removeIf(entry -> entry.getValue().expiresAt < now);
    }

}
