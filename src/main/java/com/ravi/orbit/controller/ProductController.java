package com.ravi.orbit.controller;

import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
        return new ResponseEntity<>(productService.getAllProducts(pageable), HttpStatus.OK);
    }

    @PostMapping("/handleProduct")
    public ResponseEntity<ProductDTO> handleProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.handleProduct(productDTO), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductDTOById(id), HttpStatus.OK);
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<ProductDTO> getProductByProductId(@PathVariable String productId) {
        return new ResponseEntity<>(productService.getProductDTOByProductId(productId), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getProductDTOsByName(name), HttpStatus.OK);
    }

    @DeleteMapping("deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
