package com.ravi.orbit.controller;

import com.ravi.orbit.dto.SellerDto;
import com.ravi.orbit.entity.*;
import com.ravi.orbit.service.SellerService;
import com.ravi.orbit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    private final UserService userService;

    // Get seller by ID
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) {
        Optional<Seller> seller = sellerService.getSellerById(id);
        return seller.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update seller by ID (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody SellerDto sellerDto) {
        Optional<Seller> seller = sellerService.getSellerById(id);
        if (seller.isPresent()) {
            return ResponseEntity.ok(sellerService.setSeller(sellerDto));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete seller by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        boolean isDeleted = sellerService.deleteSellerById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
