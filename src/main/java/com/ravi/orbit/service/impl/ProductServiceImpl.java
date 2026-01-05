package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.*;
import com.ravi.orbit.entity.Category;
import com.ravi.orbit.entity.Color;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.Size;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.ColorRepository;
import com.ravi.orbit.repository.ProductRepository;
import com.ravi.orbit.repository.SizeRepository;
import com.ravi.orbit.service.ICategoryService;
import com.ravi.orbit.service.IProductService;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.utils.CommonMethods;
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
public class ProductServiceImpl implements IProductService {

    private final ICategoryService categoryService;
    private final IUserService userService;

    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    public ProductDTO handleProduct(ProductDTO productDTO) {

//        Validator.validateUserSignup(categoryDTO);

        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        User user = userService.getUserById(productDTO.getSellerId());

        Product product = null;

        if(CommonMethods.isEmpty(productDTO.getId())){
            product = new Product();
            product.setCategory(category);
            product.setUser(user);
        }
        else{
            product = getProductById(productDTO.getId());
        }
        productRepository.save(mapToProductEntity(product, productDTO));

        List<SizeDTO> sizes = handleSizes(productDTO.getSizes(), product);
        List<ColorDTO> colors = handleColors(productDTO.getColors(), product);

        productDTO.setId(product.getId());
        productDTO.setSizes(sizes);
        productDTO.setColors(colors);
        productDTO.setCategoryId(category.getId());
        productDTO.setSellerId(user.getId());
        return productDTO;
    }

    public List<SizeDTO> handleSizes(List<SizeDTO> sizes, Product product){
        for(SizeDTO sizeDTO : sizes){
            Size size = null;
            if(CommonMethods.isEmpty(sizeDTO.getId())){
                size = new Size();
                size.setProduct(product);
            }
            else{
                size = getSizeById(sizeDTO.getId());
            }
            size.setSize(sizeDTO.getSize());
            size.setAvailable(sizeDTO.isAvailable());
            size.setPrice(sizeDTO.getPrice());
            size.setQuantity(sizeDTO.getQuantity());
            sizeRepository.save(size);
            sizeDTO.setId(size.getId());
        }
        return sizes;
    }

    public List<ColorDTO> handleColors(List<ColorDTO> colors, Product product){
        for(ColorDTO colorDTO : colors){
            Color color = null;
            if(CommonMethods.isEmpty(colorDTO.getId())){
                color = new Color();
                color.setProduct(product);
            }
            else{
                color = getColorById(colorDTO.getId());
            }
            color.setColor(colorDTO.getColor());
            color.setAvailable(colorDTO.isAvailable());
            color.setPrice(colorDTO.getPrice());
            color.setQuantity(colorDTO.getQuantity());
            colorRepository.save(color);
            colorDTO.setId(color.getId());
        }
        return colors;
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.getAllProducts(pageable);
    }

    @Override
    public ProductDTO getProduct(Long id){

        ProductDTO productDTO = getProductDTOById(id);

        List<SizeDTO> sizes = getSizeDTOsByProductId(id);
        if(sizes != null){
            productDTO.setSizes(sizes);
        }
        List<ColorDTO> colors = getColorDTOsByProductId(id);
        if(colors != null){
            productDTO.setColors(colors);
        }

        return productDTO;
    }

    @Override
    public ProductDTO getProductByCode(String code){

        ProductDTO productDTO = getProductDTOByCode(code);

        List<SizeDTO> sizes = getSizeDTOsByProductId(productDTO.getId());
        if(sizes != null){
            productDTO.setSizes(sizes);
        }
        List<ColorDTO> colors = getColorDTOsByProductId(productDTO.getId());
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

    public ProductDTO getProductDTOById(Long id) {
        return productRepository.getProductDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Product: " + id));
    }

    public ProductDTO getProductDTOByCode(String code) {
        return productRepository.getProductDTOByCode(code)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Product: " + code));
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

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Product: " + id));
    }

    public List<SizeDTO> getSizeDTOsByProductId(Long productId){
        return sizeRepository.getSizeDTOsByProductId(productId);
    }

    public List<ColorDTO> getColorDTOsByProductId(Long productId){
        return colorRepository.getColorDTOsByProductId(productId);
    }

    public Size getSizeById(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Size: " + id));
    }

    public Color getColorById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Color: " + id));
    }

    private Product mapToProductEntity(Product product, ProductDTO productDTO) {
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setFeatures(productDTO.getFeatures());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setMarketPrice(productDTO.getMarketPrice());
        product.setDiscountPercent(productDTO.getDiscountPercent());
        product.setDiscountAmount((productDTO.getMarketPrice() * productDTO.getDiscountPercent() / 100));
        product.setSellingPrice(productDTO.getMarketPrice() - (productDTO.getMarketPrice() * productDTO.getDiscountPercent() / 100) );
        product.setImageUrl(productDTO.getImageUrl());

        return product;
    }

}