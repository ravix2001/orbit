package com.ravi.orbit.service;

import com.ravi.orbit.dto.*;
import com.ravi.orbit.entity.Category;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.repository.ProductRepository;
import com.ravi.orbit.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final SellerService sellerService;
    private final UserRepository userRepository;

    public Product saveProduct(ProductDto productDto) {
        Optional<Category> categoryOpt = categoryService.getCategoryById(productDto.getCategoryId());
        Optional<Seller> sellerOpt = sellerService.getSellerById(productDto.getSellerId());

        if (categoryOpt.isEmpty()) {
            throw new EntityNotFoundException("Category with id = " + productDto.getCategoryId() + "not found");
        }

        if (sellerOpt.isEmpty()) {
            throw new EntityNotFoundException("Seller with id = " + productDto.getSellerId() + "not found");
        }

        return productRepository.save(mapToProductEntity(productDto, categoryOpt.get(), sellerOpt.get()));
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        return productRepository.save(mapToExistingProductEntity(id, productDto));
    }

    public List<ProductPublicResponseDto> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductPublicResponseDto).toList();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

//    public ProductPublicResponseDto getProductById(Long id) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        return mapToProductPublicResponseDto(product);
//    }

    public Optional<Product> findByProductId(String productId) {
        return productRepository.findByProductId(productId);
    }

    public List<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    public boolean deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductPublicResponseDto getProductPublicDto(Product product) {
        return mapToProductPublicResponseDto(product);
    }

    private Product mapToProductEntity(ProductDto productDto, Category category, Seller seller) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.setImages(productDto.getImages());
        product.setQuantity(productDto.getQuantity());
        product.setColor(productDto.getColor());
        product.setSize(productDto.getSize());
        product.setMarketPrice(productDto.getMarketPrice());
        product.setDiscount(productDto.getDiscount());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setCategory(category);
        product.setSeller(seller);

        return product;
    }

    private Product mapToExistingProductEntity(Long id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product with id = " + id + "not found");
        }
        Product existingProduct = product.get();
        existingProduct.setProductId(productDto.getProductId());
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setImages(productDto.getImages());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setColor(productDto.getColor());
        existingProduct.setSize(productDto.getSize());
        existingProduct.setMarketPrice(productDto.getMarketPrice());
        existingProduct.setDiscount(productDto.getDiscount());
        existingProduct.setSellingPrice(productDto.getSellingPrice());

        return existingProduct;
    }



    private ProductPublicResponseDto mapToProductPublicResponseDto(Product product) {
        ProductPublicResponseDto dto = new ProductPublicResponseDto();
        dto.setId(product.getId());
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setImages(product.getImages());
        dto.setQuantity(product.getQuantity());
        dto.setColor(product.getColor());
        dto.setSize(product.getSize());
        dto.setMarketPrice(product.getMarketPrice());
        dto.setDiscount(product.getDiscount());
        dto.setSellingPrice(product.getSellingPrice());
        dto.setNumRatings(product.getNumRatings());
        dto.setAvgRating(product.getAvgRating());
        dto.setCreatedAt(product.getCreatedAt().toString());
        dto.setCategoryName(product.getCategory().getName());
        dto.setSellerName(product.getSeller().getFullName());
        dto.setReviews(product.getReviews());

//        // Map seller public info
//        SellerPublicDto sellerDto = new SellerPublicDto();
//        Seller seller = product.getSeller();
//        sellerDto.setId(seller.getId());
//        sellerDto.setFullName(seller.getFullName());
//        sellerDto.setBusinessName(seller.getBusinessDetails().getBusinessName());
//        sellerDto.setBusinessAddress(seller.getBusinessDetails().getBusinessAddress());
//        sellerDto.setBusinessPhone(seller.getBusinessDetails().getBusinessPhone());
//        sellerDto.setBusinessWebsite(seller.getBusinessDetails().getBusinessWebsite());
//        sellerDto.setBusinessDescription(seller.getBusinessDetails().getBusinessDescription());
//        sellerDto.setBusinessLogo(seller.getBusinessDetails().getBusinessLogo());
//        sellerDto.setBusinessBanner(seller.getBusinessDetails().getBusinessBanner());
//
//        dto.setSeller(sellerDto);

//        List<ReviewDto> reviewDTOs = product.getReviews().stream().map(review -> {
//            ReviewDto r = new ReviewDto();
//            r.setId(review.getId());
//            r.setReviewerName(review.getUser().getFullName()); // Or user.getName() if using user relation
//            r.setReviewText(review.getReviewText());
//            r.setRating(review.getRating());
//            r.setCreatedAt(review.getCreatedAt().toString());
//            return r;
//        }).toList();
//
//        dto.setReviews(reviewDTOs);


        return dto;
    }

}
