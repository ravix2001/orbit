package com.ravi.orbit.controller;

import com.ravi.orbit.entity.User;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email-verification")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final UserRepository userRepository;

    // Send verification email
    @PostMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String email) {
        try {

            // just trying to get the email from the logged in user
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
//            User user = userRepository.findByUsername(username);
//            String email = user.getEmail();
            emailVerificationService.sendVerificationEmail(email);
            return ResponseEntity.ok("Verification email sent to " + email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Verify email using the token
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        boolean isVerified = emailVerificationService.verifyEmail(token);
        if (isVerified) {
            return ResponseEntity.ok("Email successfully verified");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token");
        }
    }
}
