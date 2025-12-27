package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.service.IAdminService;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
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
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    // Get user by ID
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDTOById(id));
    }

    // Get all sellers
    @GetMapping("/allSellers")
    public ResponseEntity<Page<SellerDTO>> getAllSellers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(sellerService.getAllSellers(pageable));
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
