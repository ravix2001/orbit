package com.ravi.orbit.service;

import com.ravi.orbit.dto.ProductResponseDto;
import com.ravi.orbit.entity.Cart;
import com.ravi.orbit.entity.CartItem;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.repository.CartItemRepository;
import com.ravi.orbit.repository.CartRepository;
import com.ravi.orbit.repository.ProductRepository;
import com.ravi.orbit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    /**
     * Get Cart by User ID.
     */
    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    /**
     * Add a product to the cart.
     */
    public Cart addProductToCart(Long userId, Long productId, int quantity) {         // id of product not productId so using Long and not String
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found!"));
        ProductResponseDto productResponse = productService.getProductResponse(product);

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCartItems(new HashSet<>());
            return cartRepository.save(newCart);
        });

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setMarketPrice(product.getMarketPrice()*cartItem.getQuantity());
            cartItem.setSellingPrice(product.getSellingPrice()*cartItem.getQuantity());
        }else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setMarketPrice(product.getMarketPrice()*quantity);
            newCartItem.setSellingPrice(product.getSellingPrice()*quantity);
            cart.getCartItems().add(newCartItem);
        }

        updateCartTotals(cart);
        return cartRepository.save(cart);
    }

    /**
     * Remove a product from the cart.
     */
    public boolean removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Cart not found!"));
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
        updateCartTotals(cart);
        cartRepository.save(cart);
        return true;
    }

    /**
     * Update the totals for the cart.
     */
    private void updateCartTotals(Cart cart) {
        int totalItems = 0;
        double totalMarketPrice = 0;
        double totalSellingPrice = 0;

        for (CartItem cartItem : cart.getCartItems()){
            totalItems += cartItem.getQuantity();
            totalMarketPrice += cartItem.getMarketPrice();
            totalSellingPrice += cartItem.getSellingPrice();
        }

        cart.setTotalItems(totalItems);
        cart.setTotalMarketPrice(totalMarketPrice);
        cart.setTotalSellingPrice(totalSellingPrice);
    }

    /**
     * Clear all items in the cart.
     */
    public boolean clearCart(Long userId) {
        boolean user = userRepository.existsById(userId);
        if (!user) {
            throw new IllegalArgumentException("User not found!");
        }
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Cart not found!"));
        cart.getCartItems().clear();

        cart.setTotalItems(0);
        cart.setTotalMarketPrice(0);
        cart.setTotalSellingPrice(0);
        cart.setDiscount(0);

        cartRepository.save(cart);
        return true;
    }


}
