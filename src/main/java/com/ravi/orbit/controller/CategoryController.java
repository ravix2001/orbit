package com.ravi.orbit.controller;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/handleCategory")
    public ResponseEntity<CategoryDTO> handleCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.handleCategory(categoryDTO));
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
//        return ResponseEntity.ok(categoryService.getAllCategories());
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryDTO> getCategoryDTOById(@PathVariable Long id) {
//        return ResponseEntity.ok(categoryService.getCategoryDTOById(id));
//    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteCategoryHard/{id}")
    public ResponseEntity<?> deleteCategoryHard(@PathVariable Long id) {
        categoryService.deleteCategoryHard(id);
        return ResponseEntity.ok().build();
    }

}
