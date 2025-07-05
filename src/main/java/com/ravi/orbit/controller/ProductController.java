package com.ravi.orbit.controller;

import com.ravi.orbit.dto.ProductDto;
import com.ravi.orbit.dto.ProductResponseDto;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    /**
     * Get paginated products
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductResponseDto>> getAllProductsPaginated(@RequestParam(defaultValue = "0") int page, // Default to page 0
                                                                            @RequestParam(defaultValue = "20") int size // Default page size is 20
                                                                                  ){
        Page<ProductResponseDto> paginatedProducts = productService.getAllPaginated(page, size);
        return ResponseEntity.ok(paginatedProducts);
    }

    /**
     * Get all products
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    /**
     * Get product by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> ResponseEntity.ok(productService.getProductResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ProductPublicResponseDto> getProductById(@PathVariable Long id) {
//        return ResponseEntity.ok(productService.getProductById(id));
//    }

    /**
     * Get product by productId
     */
    @GetMapping("/productId/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable String productId) {
        Optional<Product> product = productService.findByProductId(productId);
        return product.map(value -> ResponseEntity.ok(productService.getProductResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get product by productName
     */
    @GetMapping("/productName/{name}")
    public ResponseEntity<List<ProductResponseDto>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> getProductByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.saveProduct(productDto);
        ProductResponseDto productResponseDto = productService.getProductResponse(newProduct);
        URI location = URI.create("/api/product/" + newProduct.getProductId());
        return ResponseEntity.created(location).body(productResponseDto);
    }

    // change these to id instead of productId
    @PatchMapping("/updateProduct/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            Product updatedProduct = productService.updateProduct(id, productDto);
            ProductResponseDto productResponseDto = productService.getProductResponse(updatedProduct);
            return ResponseEntity.ok(productResponseDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
