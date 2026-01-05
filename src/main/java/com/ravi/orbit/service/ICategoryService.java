package com.ravi.orbit.service;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {

    CategoryDTO handleCategory(CategoryDTO categoryDTO);

    Page<CategoryDTO> getAllCategories(Pageable pageable);

    CategoryDTO getCategoryDTOById(Long id);

    void deleteCategory(Long id);

    void deleteCategoryHard(Long id);

    Category getCategoryById(Long id);

}
