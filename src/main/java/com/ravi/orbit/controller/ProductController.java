package com.ravi.orbit.controller;

import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @PostMapping("/handleProduct")
    public ResponseEntity<ProductDTO> handleProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.handleProduct(productDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDTOById(id));
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<ProductDTO> getProductByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductDTOByProductId(productId));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductDTOsByName(name));
    }

    @DeleteMapping("deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteProductHard/{id}")
    public ResponseEntity<Void> deleteProductHard(@PathVariable Long id) {
        productService.deleteProductHard(id);
        return ResponseEntity.ok().build();
    }

}
