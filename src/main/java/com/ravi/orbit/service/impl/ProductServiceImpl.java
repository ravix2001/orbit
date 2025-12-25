package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.*;
import com.ravi.orbit.entity.Category;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.ProductRepository;
import com.ravi.orbit.service.ICategoryService;
import com.ravi.orbit.service.IProductService;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.MyConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final ISellerService sellerService;

    @Override
    public ProductDTO handleProduct(ProductDTO productDTO) {

//        Validator.validateUserSignup(categoryDTO);

        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        Seller seller = sellerService.getSellerById(productDTO.getSellerId());

        Product product = null;

        if(CommonMethods.isEmpty(productDTO.getId())){
            product = new Product();
            product.setCategory(category);
            product.setSeller(seller);
        }
        else{
            product = getProductById(productDTO.getId());
        }
        productRepository.save(mapToProductEntity(product, productDTO));

        productDTO.setId(product.getId());
        return productDTO;
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.getAllProducts(pageable);
    }

    @Override
    public ProductDTO getProductDTOById(Long id) {
        return productRepository.getProductDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Product: " + id));
    }

    @Override
    public ProductDTO getProductDTOByProductId(String productId) {
        return productRepository.getProductDTOByProductId(productId)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Product: " + productId));
    }

    @Override
    public List<ProductDTO> getProductDTOsByName(String name) {
        return productRepository.getProductDTOsByName(name);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setStatus(EStatus.DELETED);
        productRepository.save(product);
    }

    @Override
    public void deleteProductHard(Long id) {   // remaining to delete its children
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Product: " + id));
    }
    private Product mapToProductEntity(Product product, ProductDTO productDTO) {
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setMarketPrice(productDTO.getMarketPrice());
        product.setDiscountPercent(productDTO.getDiscountPercent());
        product.setDiscountAmount((productDTO.getMarketPrice() * productDTO.getDiscountPercent() / 100));
        product.setSellingPrice(productDTO.getMarketPrice() - (productDTO.getMarketPrice() * productDTO.getDiscountPercent() / 100) );
        product.setImgUrl(productDTO.getImgUrl());

        return product;
    }

}