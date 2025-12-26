package com.ravi.orbit.controller;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.service.IPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final IPublicService publicService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Running");
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(publicService.getAllCategories());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryDTOById(@PathVariable Long id) {
        return ResponseEntity.ok(publicService.getCategoryDTOById(id));
    }

    @GetMapping("/products/paginated")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(publicService.getAllProducts(pageable));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(publicService.getProduct(id));
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getProductByName(@RequestParam String name) {
        return ResponseEntity.ok(publicService.getProductDTOsByName(name));
    }

}
