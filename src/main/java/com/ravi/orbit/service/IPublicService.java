package com.ravi.orbit.service;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPublicService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryDTOById(Long id);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO getProduct(Long id);

    List<ProductDTO> getProductDTOsByName(String name);

}
