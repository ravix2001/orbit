package com.ravi.orbit.service;

import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    ProductDTO handleProduct(ProductDTO productDTO);

//    Page<ProductDTO> getAllProducts(Pageable pageable);
//
//    ProductDTO getProduct(Long id);

    ProductDTO getProductDTOById(Long id);

//    ProductDTO getProductDTOByProductId(String productId);
//
//    List<ProductDTO> getProductDTOsByName(String name);

    void deleteProduct(Long id);

    void deleteProductHard(Long id);

    Product getProductById(Long id);

}
