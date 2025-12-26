package com.ravi.orbit.service;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.entity.Category;

import java.util.List;

public interface ICategoryService {

    CategoryDTO handleCategory(CategoryDTO categoryDTO);

//    List<CategoryDTO> getAllCategories();
//
//    CategoryDTO getCategoryDTOById(Long id);

    void deleteCategory(Long id);

    void deleteCategoryHard(Long id);

    Category getCategoryById(Long id);

}
