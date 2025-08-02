package com.ravi.orbit.service;

import com.ravi.orbit.dto.*;
import com.ravi.orbit.entity.Category;
import com.ravi.orbit.entity.Product;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.repository.ProductRepository;
import com.ravi.orbit.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final SellerService sellerService;

    @Transactional
    public Product saveProduct(ProductDto productDto) {
        List<Long> categoryIds = productDto.getCategoryIds(); // Assuming `categoryIds` is provided in ProductDto
        List<Category> categories = categoryService.getCategoriesByIds(categoryIds); // Implement this in CategoryService

        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Categories with provided IDs not found");
        }

        return productRepository.save(mapToProductEntity(productDto, categories));
    }


    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        return productRepository.save(mapToExistingProductEntity(id, productDto));
    }

    public Page<ProductResponseDto> getAllPaginated(int pageNumber, int pageSize) {
        return productRepository.findAll(org.springframework.data.domain.PageRequest.of(pageNumber, pageSize))
                .map(this::mapToProductResponse);
    }

    public List<ProductResponseDto> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse).toList();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

//    public ProductResponseDto getProductById(Long id) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        return mapToProductResponse(product);
//    }

    public Optional<Product> findByProductId(String productId) {
        return productRepository.findByProductId(productId);
    }

    public List<ProductResponseDto> findByName(String name){
        List<Product> products = productRepository.findByName(name);
        return products.stream()
                .map(this::mapToProductResponse).toList();
    }

    public List<ProductResponseDto> findByCategory(Long categoryId, int pageNumber, int pageSize) {
        List<Product> products = productRepository.findByCategories_Id(categoryId, org.springframework.data.domain.PageRequest.of(pageNumber, pageSize));
        return products.stream()
                .map(this::mapToProductResponse).toList();
    }

    public List<ProductResponseDto> findBySeller(String username) {
        List<Product> products = productRepository.findBySeller_Username(username);
        return products.stream()
                .map(this::mapToProductResponse).toList();
    }

    public boolean deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductResponseDto getProductResponse(Product product) {
        return mapToProductResponse(product);
    }

    private Product mapToProductEntity(ProductDto productDto, List<Category> categories) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Seller seller = sellerService.findByUsername(username);

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
//        product.setSellingPrice(productDto.getSellingPrice());
        product.setSellingPrice(productDto.getMarketPrice() - (productDto.getMarketPrice() * productDto.getDiscount() / 100) );
        product.setCategories(new HashSet<>(categories)); // Creates a mutable copy
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



    private ProductResponseDto mapToProductResponse(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
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
        dto.setReviews(product.getReviews());
        // Map the categories to a List<String> containing category names
//        List<String> categoryNames = product.getCategories()
//                .stream()
//                .map(Category::getName)
//                .toList();
//        dto.setCategoryNames(categoryNames);

        // Map Seller to SellerResponse
        Seller seller = product.getSeller();
        if (seller != null) {
            SellerResponseDto sellerResponse = new SellerResponseDto();
            sellerResponse.setId(seller.getId());
            sellerResponse.setFullName(seller.getFullName());
            sellerResponse.setBusinessName(seller.getBusinessDetails().getBusinessName());
            sellerResponse.setBusinessPhone(seller.getBusinessDetails().getBusinessPhone());
            sellerResponse.setBusinessLogo(seller.getBusinessDetails().getBusinessLogo());
            dto.setSeller(sellerResponse);
        }


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
//        dto.setReviews(product.getReviews());

        return dto;
    }

}