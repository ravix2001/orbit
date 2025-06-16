package com.ravi.orbit.controller;

import com.ravi.orbit.dto.ProductDto;
import com.ravi.orbit.dto.ProductPublicResponseDto;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductPublicResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPublicResponseDto> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> ResponseEntity.ok(productService.getProductPublicDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ProductPublicResponseDto> getProductById(@PathVariable Long id) {
//        return ResponseEntity.ok(productService.getProductById(id));
//    }

    @GetMapping("productId/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        Optional<Product> product = productService.findByProductId(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/productName{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @PostMapping()
    public ResponseEntity<ProductPublicResponseDto> createProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.saveProduct(productDto);
        ProductPublicResponseDto productResponseDto = productService.getProductPublicDto(newProduct);
        URI location = URI.create("/api/product/" + newProduct.getProductId());
        return ResponseEntity.created(location).body(productResponseDto);
    }

    // change these to id instead of productId
    @PatchMapping("/{id}")
    public ResponseEntity<ProductPublicResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            Product updatedProduct = productService.updateProduct(id, productDto);
            ProductPublicResponseDto productResponseDto = productService.getProductPublicDto(updatedProduct);
            return ResponseEntity.ok(productResponseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
