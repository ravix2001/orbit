package com.ravi.orbit.controller;

import com.ravi.orbit.dto.CategoryDto;
import com.ravi.orbit.entity.Category;
import com.ravi.orbit.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

//    category controller milaauna baaki xa tes paxi product controller pni check garnu parxa

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setLevel(categoryDto.getLevel());

        if (categoryDto.getParentCategoryId() != null) {
            Category parent = categoryService.getCategoryById(categoryDto.getParentCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            category.setParentCategory(parent);
        }

        Category savedCategory = categoryService.saveCategory(category);

        URI location = URI.create("/api/category/" + savedCategory.getId());
        return ResponseEntity.created(location).body(savedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(categoryDto.getName());
        category.setLevel(categoryDto.getLevel());

        if (categoryDto.getParentCategoryId() != null) {
            Category parent = categoryService.getCategoryById(categoryDto.getParentCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            category.setParentCategory(parent);
        } else {
            category.setParentCategory(null);
        }

        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategoryById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
