package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.service.IAdminService;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;
    private final IUserService userService;
    private final ISellerService sellerService;

    @PostMapping("/handleUser")
    public ResponseEntity<UserDTO> handleUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.handleUser(userDTO));
    }

    @GetMapping("/createAdmin/{id}")
    public ResponseEntity<String> createAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.createAdmin(id));
    }

    @GetMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }

    // Get all users
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDTOById(id));
    }

    // Get all sellers
    @GetMapping("/allSellers")
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    // Get seller by ID
    @GetMapping("/seller/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {
        return ResponseEntity.ok(sellerService.getSellerDTOById(id));
    }

    @DeleteMapping("/deleteUserHard/{id}")
    public ResponseEntity<Void> deleteUserHard(@PathVariable Long id) {
        userService.deleteUserHard(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteSellerHard/{id}")
    public ResponseEntity<Void> deleteSellerHard(@PathVariable Long id) {
        sellerService.deleteSellerHard(id);
        return ResponseEntity.ok().build();
    }

}
