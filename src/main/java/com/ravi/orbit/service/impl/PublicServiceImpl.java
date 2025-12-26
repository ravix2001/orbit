package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.dto.ColorGroupDTO;
import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.dto.SizeGroupDTO;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.CategoryRepository;
import com.ravi.orbit.repository.ProductRepository;
import com.ravi.orbit.service.IModifierService;
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
    private final IModifierService modifierService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        return categoryRepository.getCategoryDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Category: " + id));
    }

    @Override
    public List<ProductDTO> getProductDTOsByCategoryId(Long categoryId){
        return productRepository.getProductDTOsByCategoryId(categoryId);
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.getAllProducts(pageable);
    }

    @Override
    public ProductDTO getProduct(Long id){

        ProductDTO productDTO = productService.getProductDTOById(id);

        SizeGroupDTO sizeGroupDTO = modifierService.getSizeGroupByProductId(id);
        if(sizeGroupDTO != null){
            productDTO.setSizes(sizeGroupDTO.getSizes());
        }
        ColorGroupDTO colorGroupDTO = modifierService.getColorGroupByProductId(id);
        if(colorGroupDTO != null){
            productDTO.setColors(colorGroupDTO.getColors());
        }

        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductDTOsByName(String name) {
        return productRepository.getProductDTOsByName(name);
    }

}
