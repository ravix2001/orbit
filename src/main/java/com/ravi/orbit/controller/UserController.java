package com.ravi.orbit.controller;

import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(userService.getUserDTOByUsername(username), HttpStatus.OK);
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
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
