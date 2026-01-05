package com.ravi.orbit.controller;

import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.service.IAdminService;
import com.ravi.orbit.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;
    private final IUserService userService;

//    @PostMapping("/handleUser")
//    public ResponseEntity<UserDTO> handleUser(@RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.handleUser(userDTO));
//    }

    @GetMapping("/createAdmin/{id}")
    public ResponseEntity<String> createAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.createAdmin(id));
    }

    @GetMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }

    // Get user by ID
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDTOById(id));
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam ERole role, EStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(adminService.getUsersByRoleAndStatus(role, status, pageable));
    }

//    // Get all sellers
//    @GetMapping("/allSellers")
//    public ResponseEntity<Page<UserDTO>> getAllSellers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "true") boolean ascending) {
//
//        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return ResponseEntity.ok(adminService.getAllSellers(pageable));
//    }
//
//    // Get all admins
//    @GetMapping("/allAdmins")
//    public ResponseEntity<Page<UserDTO>> getAllAdmins(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "true") boolean ascending) {
//
//        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return ResponseEntity.ok(adminService.getAllAdmins(pageable));
//    }

    @DeleteMapping("/deleteUserHard/{id}")
    public ResponseEntity<Void> deleteUserHard(@PathVariable Long id) {
        userService.deleteUserHard(id);
        return ResponseEntity.ok().build();
    }

}
