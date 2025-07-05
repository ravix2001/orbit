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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final SellerService sellerService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/userSignup")
    public ResponseEntity<?> userSignup(@RequestBody UserDto signupDto) throws Exception {
        try{
            User savedUser = userService.saveNewUser(signupDto);
            URI location = URI.create("/api/user/profile" + savedUser.getId());
            return ResponseEntity.created(location).body(savedUser);
        }catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/sellerSignup")
    public ResponseEntity<?> sellerSignup(@RequestBody SellerDto sellerDto) {
        try{
            Seller savedSeller = sellerService.saveNewSeller(sellerDto);
            URI location = URI.create("/api/seller/profile" + savedSeller.getId());
            return ResponseEntity.created(location).body(savedSeller);
        }catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/userLogin")
    public ResponseEntity<String> userLogin(@RequestBody LoginDto loginDto) {
        try{
            User user = new User();
            user.setUsername(loginDto.getUsername());
            user.setPassword(loginDto.getPassword());
            // authenticate
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
//            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
//            return jwtToken;
            log.info("User {} successfully logged in", user.getUsername());
            return ResponseEntity.ok("Login successful");
        }catch (Exception e){
            log.error("Error logging in user: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @PostMapping("/sellerLogin")
    public ResponseEntity<String> sellerLogin(@RequestBody LoginDto loginDto) {
        try{
            Seller seller = new Seller();
            seller.setUsername(loginDto.getUsername());
            seller.setPassword(loginDto.getPassword());
            // authenticate
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(seller.getUsername(), seller.getPassword()));
//            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
//            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
//            return jwtToken;
            log.info("Seller {} successfully logged in", seller.getUsername());
            return ResponseEntity.ok("Login successful");
        }catch (Exception e){
            log.error("Error logging in seller: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

}
