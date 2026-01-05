package com.ravi.orbit.service;

import com.ravi.orbit.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    ProductDTO handleProduct(ProductDTO productDTO);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO getProduct(Long id);

    ProductDTO getProductByCode(String code);

    Page<ProductDTO> getProductDTOsByName(Pageable pageable, String name);

    Page<ProductDTO> getProductDTOsByCategoryId(Pageable pageable, Long categoryId);

    Page<ProductDTO> getProductDTOsBySellerId(Pageable pageable, Long sellerId);

    void deleteProduct(Long id);

    void deleteProductHard(Long id);

}
