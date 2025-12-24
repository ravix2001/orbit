package com.ravi.orbit.controller;

import com.ravi.orbit.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email-verification")
public class EmailVerificationController {

    private final IEmailService emailService;

    // Send verification email
    @GetMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam String email) {
        return ResponseEntity.ok(emailService.sendVerificationEmail(email));
    }

    // Verify email using the token
    @GetMapping("/verify-email-user")
    public ResponseEntity<String> verifyEmailForUser(@RequestParam String token) {
        return ResponseEntity.ok(emailService.verifyEmailForUser(token));
    }

    @GetMapping("/verify-email-seller")
    public ResponseEntity<String> verifyEmailForSeller(@RequestParam String token) {
        return ResponseEntity.ok(emailService.verifyEmailForSeller(token));
    }

}
