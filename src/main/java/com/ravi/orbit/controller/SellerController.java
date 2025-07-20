package com.ravi.orbit.controller;

import com.ravi.orbit.dto.ProductResponseDto;
import com.ravi.orbit.dto.SellerDto;
import com.ravi.orbit.entity.*;
import com.ravi.orbit.service.ProductService;
import com.ravi.orbit.service.SellerService;
import com.ravi.orbit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    private final ProductService productService;

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Seller seller = sellerService.findByUsername(username);
        return ResponseEntity.ok(seller);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProductByCategory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(productService.findBySeller(username));
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<Seller> updateSeller(@RequestBody SellerDto sellerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(sellerService.saveExistingSeller(username, sellerDto));
    }

    // to be fixed later
    @DeleteMapping("/delete-profile")
    public ResponseEntity<Void> deleteSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        sellerService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
