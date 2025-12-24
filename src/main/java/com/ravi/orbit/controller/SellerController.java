package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.service.ISellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final ISellerService sellerService;

    @GetMapping("/profile")
    public ResponseEntity<SellerDTO> getSellerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(sellerService.getSellerDTOByUsername(username), HttpStatus.OK);
    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User updatedUser = userService.saveExistingUser(username, userDto);
//        return ResponseEntity.ok(updatedUser);
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        sellerService.deleteSeller(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
