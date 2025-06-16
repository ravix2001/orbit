package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDto;
import com.ravi.orbit.dto.UserDto;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.service.SellerService;
import com.ravi.orbit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/userSignup")
    public ResponseEntity<User> userSignup(@RequestBody UserDto signupDto) {
        User newUser = userService.setUser(signupDto);
        URI location = URI.create("/api/user/" + newUser.getId());
        return ResponseEntity.created(location).body(newUser);
    }

    @PostMapping("/sellerSignup")
    public ResponseEntity<Seller> sellerSignup(@RequestBody SellerDto sellerDto) {
        Seller savedSeller = sellerService.setSeller(sellerDto);
        URI location = URI.create("/api/seller/" + savedSeller.getId());
        return ResponseEntity.created(location).body(savedSeller);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
//        try{
//            User user = new User();
//            user.setUsername(loginDto.getUsername());
//            user.setPassword(loginDto.getPassword());
//            // authenticate
//
//            log.info("User {} logged in", user.getUsername());
//            return ResponseEntity.ok("Login successful");
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Invalid credentials");
//        }
//    }

}
