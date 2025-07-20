package com.ravi.orbit.service;

import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.ACCOUNT_STATUS;
import com.ravi.orbit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailService emailService;
    private final Map<String, String> verificationTokens = new HashMap<>(); // Temporary store for tokens
    private final UserRepository userRepository;

    // Initiate email verification
    public void sendVerificationEmail(String email) {
        // Check if email exists in the database
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Email not found");
        }

        // Generate a verification token
        String token = UUID.randomUUID().toString();
        verificationTokens.put(email, token);

        // Build verification link
//        String verificationLink = "http://your-domain.com/api/verify-email?token=" + token;
        String verificationLink = "http://localhost:8080/api/verify-email?token=" + token;
        // Send verification email
        String subject = "Verify Your Email Address";
        String body = "Click the link below to verify your email:\n" + verificationLink;
        emailService.sendEmail(email, subject, body);
    }

    // Verify the email using the token
    public boolean verifyEmail(String token) {
        for (Map.Entry<String, String> entry : verificationTokens.entrySet()) {
            if (entry.getValue().equals(token)) {
                // Mark the email as verified
                String email = entry.getKey();
                User user = userRepository.findByEmail(email);
                if (user != null) {
                    user.setAccountStatus(ACCOUNT_STATUS.ACTIVE);
//                    user.setVerified(true); // Assumes a 'verified' field exists in the User entity
                    userRepository.save(user);
                }

                // Remove token after verification
                verificationTokens.remove(email);
                return true;
            }
        }
        return false; // Token not found or invalid
    }


}
