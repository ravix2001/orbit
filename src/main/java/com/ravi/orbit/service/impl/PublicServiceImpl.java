package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.dto.ColorDTO;
import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.dto.SizeDTO;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.CategoryRepository;
import com.ravi.orbit.repository.ProductRepository;
//import com.ravi.orbit.service.IModifierService;
import com.ravi.orbit.service.IProductService;
import com.ravi.orbit.service.IPublicService;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements IPublicService {

    private final IProductService productService;
//    private final IModifierService modifierService;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.getAllCategories(pageable);
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        return categoryRepository.getCategoryDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Category: " + id));
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.getAllProducts(pageable);
    }

    @Override
    public ProductDTO getProduct(Long id){

        ProductDTO productDTO = productService.getProductDTOById(id);

        List<SizeDTO> sizes = productService.getSizeDTOsByProductId(id);
        if(sizes != null){
            productDTO.setSizes(sizes);
        }
        List<ColorDTO> colors = productService.getColorDTOsByProductId(id);
        if(colors != null){
            productDTO.setColors(colors);
        }

        return productDTO;
    }

    @Override
    public Page<ProductDTO> getProductDTOsByName(Pageable pageable, String name) {
        return productRepository.getProductDTOsByName(pageable, name);
    }

    @Override
    public Page<ProductDTO> getProductDTOsByCategoryId(Pageable pageable, Long categoryId){
        return productRepository.getProductDTOsByCategoryId(pageable, categoryId);
    }

    @Override
    public Page<ProductDTO> getProductDTOsBySellerId(Pageable pageable, Long sellerId) {
        return productRepository.getProductDTOsBySellerId(pageable, sellerId);
    }

}
