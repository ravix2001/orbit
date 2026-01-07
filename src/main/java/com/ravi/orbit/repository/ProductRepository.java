package com.ravi.orbit.repository;

import com.ravi.orbit.dto.ProductDTO;
import com.ravi.orbit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.name, p.brand, p.description, p.quantity, " +
            "p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice," +
            "p.categoryId, c.name, p.userId, p.imageUrl ) " +
            " FROM Product p" +
            " LEFT JOIN Category c ON p.categoryId = c.id " +
            " WHERE p.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<ProductDTO> getAllProducts(Pageable pageable);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.code, p.name, p.brand, p.status, p.features, " +
            "p.description, p.quantity, p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice," +
            "p.categoryId, c.name, p.userId, p.imageUrl ) " +
            " FROM Product p" +
            " LEFT JOIN Category c ON p.categoryId = c.id " +
            " WHERE p.id = :id")
    Optional<ProductDTO> getProductDTOById(Long id);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.code, p.name, p.brand, p.status, p.features, " +
            "p.description, p.quantity, p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice," +
            "p.categoryId, c.name, p.userId, p.imageUrl ) " +
            " FROM Product p" +
            " LEFT JOIN Category c ON p.categoryId = c.id " +
            " WHERE p.code = :code")
    Optional<ProductDTO> getProductDTOByCode(String code);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.name, p.brand, p.description, p.quantity, " +
            "p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice," +
            "p.categoryId, c.name, p.userId, p.imageUrl ) " +
            " FROM Product p" +
            " LEFT JOIN Category c ON p.categoryId = c.id " +
            " WHERE p.name = :name AND p.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<ProductDTO> getProductDTOsByName(Pageable pageable, String name);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.name, p.brand, p.description, p.quantity, " +
            "p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice," +
            "p.categoryId, c.name, p.userId, p.imageUrl ) " +
            " FROM Product p" +
            " LEFT JOIN Category c ON p.categoryId = c.id " +
            " WHERE p.categoryId = :categoryId AND p.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<ProductDTO> getProductDTOsByCategoryId(Pageable pageable, Long categoryId);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.name, p.brand, p.description, p.quantity, " +
            "p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice," +
            "p.categoryId, c.name, p.userId, p.imageUrl ) " +
            " FROM Product p" +
            " LEFT JOIN Category c ON p.categoryId = c.id " +
            " WHERE p.userId = :sellerId AND p.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<ProductDTO> getProductDTOsBySellerId(Pageable pageable, Long sellerId);

}
