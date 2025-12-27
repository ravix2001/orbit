package com.ravi.orbit.service;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPublicService {

    Page<CategoryDTO> getAllCategories(Pageable pageable);

    CategoryDTO getCategoryDTOById(Long id);

    Page<ProductDTO> getProductDTOsByCategoryId(Pageable pageable, Long categoryId);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO getProduct(Long id);

    Page<ProductDTO> getProductDTOsByName(Pageable pageable, String name);

    Page<ProductDTO> getProductDTOsBySellerId(Pageable pageable, Long sellerId);

}
