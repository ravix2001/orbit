package com.ravi.orbit.controller;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/handleCategory")
    public ResponseEntity<CategoryDTO> handleCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.handleCategory(categoryDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryDTOById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteCategoryHard/{id}")
    public ResponseEntity<?> deleteCategoryHard(@PathVariable Long id) {
        categoryService.deleteCategoryHard(id);
        return ResponseEntity.ok().build();
    }

}
