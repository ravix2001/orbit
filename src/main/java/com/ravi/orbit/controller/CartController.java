package com.ravi.orbit.controller;

import com.ravi.orbit.dto.CartItemRequestDto;
import com.ravi.orbit.entity.Cart;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.findByUserId(userId);
        if (cart.isPresent()) {
            // Filter seller details when accessing products in cart items
            cart.get().getCartItems().forEach(cartItem -> {
                Product product = cartItem.getProduct();
                cartItem.setProduct(mapToProductWithPublicSeller(product));
            });
            return ResponseEntity.ok(cart.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Optional utility method for filtering seller details
    private Product mapToProductWithPublicSeller(Product product) {
        product.setSeller(null); // nullify seller details if unused
        return product;
    }

    @PostMapping
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartItemRequestDto cartItemRequestDto) {
        try{
            Cart updatedCart = cartService.addProductToCart(cartItemRequestDto.getUserId(), cartItemRequestDto.getProductId(), cartItemRequestDto.getQuantity());
            return ResponseEntity.ok(updatedCart);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        boolean isDeleted = cartService.removeProductFromCart(userId, productId);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        boolean isCleared = cartService.clearCart(userId);
        if(isCleared){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}