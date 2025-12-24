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
    @PostMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody String email) {
        return ResponseEntity.ok(emailService.sendVerificationEmail(email));
    }

    // Verify email using the token
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(emailService.verifyEmail(token));
    }
}
