package com.ravi.orbit.controller;

import com.ravi.orbit.dto.LoginDto;
import com.ravi.orbit.dto.SellerDto;
import com.ravi.orbit.dto.UserDto;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.exceptions.UserAlreadyExistsException;
import com.ravi.orbit.service.SellerService;
import com.ravi.orbit.service.UserDetailsServiceImpl;
import com.ravi.orbit.service.UserService;
import com.ravi.orbit.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final SellerService sellerService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final JwtUtil jwtUtil;

    @PostMapping("/userSignup")
    public ResponseEntity<Map<String, String>> userSignup(@RequestBody UserDto userDto) {
        try {
            // Save the new user
            User savedUser = userService.saveNewUser(userDto);

            // Generate tokens for the new user, allowing immediate login
            String accessToken = jwtUtil.generateJwtToken(savedUser.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(savedUser.getUsername());

            URI location = URI.create("/api/user/profile/" + savedUser.getId());

            // Respond with tokens and user profile location
            return ResponseEntity.created(location).body(Map.of("accessToken", accessToken, "refreshToken", refreshToken
//                    "userId", savedUser.getId().toString()
        ));
    } catch (UserAlreadyExistsException e) {
        // Handle duplicate user error
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}

    @PostMapping("/sellerSignup")
    public ResponseEntity<Map<String, String>> sellerSignup(@RequestBody SellerDto sellerDto) {
        try{
            Seller savedSeller = sellerService.saveNewSeller(sellerDto);

            String accessToken = jwtUtil.generateJwtToken(savedSeller.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(savedSeller.getUsername());

            URI location = URI.create("/api/seller/profile" + savedSeller.getId());
            return ResponseEntity.created(location).body(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        }catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> userLogin(@RequestBody LoginDto loginDto) {
        try {
            // Create a user object and set credentials from the login DTO
            User user = new User();
            user.setUsername(loginDto.getUsername());
            user.setPassword(loginDto.getPassword());

            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Load user details from the UserDetailsService implementation
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());

            // Generate the access token (short-lived)
            String accessToken = jwtUtil.generateJwtToken(userDetails.getUsername());

            // Generate the refresh token (long-lived)
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            // Log successful login
            log.info("User {} successfully logged in", user.getUsername());

            // Return both tokens as a JSON response
            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));
        } catch (Exception e) {
            // Log the error and return an error response for invalid authentication attempts
            log.error("Error logging in user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }
    }

//    @PostMapping("/sellerLogin")
//    public ResponseEntity<Map<String, String>> sellerLogin(@RequestBody LoginDto loginDto) {
//        try{
//            Seller seller = new Seller();
//            seller.setUsername(loginDto.getUsername());
//            seller.setPassword(loginDto.getPassword());
//            // Authenticate the user
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(seller.getUsername(), seller.getPassword())
//            );
//
//            // Load user details (in this case, seller details) from the UserDetailsService implementation
//            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(seller.getUsername());
//
//            // Generate the access token (short-lived)
//            String accessToken = jwtUtil.generateJwtToken(userDetails.getUsername());
//
//            // Generate the refresh token (long-lived)
//            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
//
//            // Log successful login
//            log.info("Seller {} successfully logged in", seller.getUsername());
//
//            // Return both tokens as a JSON response
//            return ResponseEntity.ok(Map.of(
//                    "accessToken", accessToken,
//                    "refreshToken", refreshToken
//            ));
//        } catch (Exception e) {
//            // Log the error and return an error response for invalid authentication attempts
//            log.error("Error logging in seller: {}", e.getMessage());
//            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
//        }
//    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        try{
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

        }catch(Exception e){
            log.error("Error refreshing token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }

    }

}