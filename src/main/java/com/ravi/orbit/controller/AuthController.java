package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.service.IAuthService;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final ISellerService sellerService;
    private final IAuthService authService;

    @PostMapping("/userSignup")
    public ResponseEntity<AuthDTO> userSignup(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.userSignup(userDTO));
    }

    @PostMapping("/sellerSignup")
    public ResponseEntity<AuthDTO> sellerSignup(@RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.sellerSignup(sellerDTO));
    }

    @PostMapping("/userLogin")
    public ResponseEntity<AuthDTO> userLogin(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.userLogin(userDTO));
    }

    @PostMapping("/sellerLogin")
    public ResponseEntity<AuthDTO> sellerLogin(@RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.sellerLogin(sellerDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.refreshToken(authHeader));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.logout(authHeader));
    }

}