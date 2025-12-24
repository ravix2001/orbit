package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.AuthPayload;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.service.impl.SellerServiceImpl;
import com.ravi.orbit.service.impl.UserServiceImpl;
import com.ravi.orbit.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    private final ISellerService sellerService;

    private final JwtUtil jwtUtil;

    @PostMapping("/userSignup")
    public ResponseEntity<AuthPayload> userSignup(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.userSignup(userDTO));
    }

    @PostMapping("/sellerSignup")
    public ResponseEntity<AuthPayload> sellerSignup(@RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.sellerSignup(sellerDTO));
    }

    @PostMapping("/userlogin")
    public ResponseEntity<AuthPayload> userLogin(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.userLoginNew(userDTO));
    }

    @PostMapping("/sellerlogin")
    public ResponseEntity<AuthPayload> sellerLogin(@RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.sellerLogin(sellerDTO));
    }

    // need to be reworked
    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");

            if (refreshToken == null) throw new IllegalArgumentException("Refresh token is missing");

            // validate the refresh token
            String username = jwtUtil.extractUsername(refreshToken);
            if (jwtUtil.isTokenExpired(refreshToken)) {
                throw new IllegalArgumentException("Refresh token expired");
            }

            // Generate a new access token
            String newAccessToken = jwtUtil.generateJwtToken(username);

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));

        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }

    }

}