package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.entity.Category;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.CategoryRepository;
import com.ravi.orbit.service.ICategoryService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.MyConstants;
import com.ravi.orbit.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO handleCategory(CategoryDTO categoryDTO) {

//        Validator.validateUserSignup(categoryDTO);

        Category category = null;

        if(CommonMethods.isEmpty(categoryDTO.getId())){
            category = new Category();
        }
        else{
            category = getCategoryById(categoryDTO.getId());
        }
        category.setName(categoryDTO.getName());
        category.setImgUrl(categoryDTO.getImgUrl());
        categoryRepository.save(category);

        categoryDTO.setId(category.getId());
        return categoryDTO;
    }

//    @Override
//    public List<CategoryDTO> getAllCategories() {
//        return categoryRepository.getAllCategories();
//    }
//
//    @Override
//    public CategoryDTO getCategoryDTOById(Long id) {
//        return categoryRepository.getCategoryDTOById(id)
//                .orElseThrow(() -> new BadRequestException(MyConstants
//                        .ERR_MSG_NOT_FOUND + "Category: " + id));
//    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        category.setStatus(EStatus.DELETED);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryHard(Long id) {   // remaining to delete its children
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Category: " + id));
    }

}