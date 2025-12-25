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

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.productId, p.name, p.brand, p.description, " +
            "p.status, p.quantity, p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice, p.imgUrl ) " +
            " FROM Product p" +
            " WHERE p.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<ProductDTO> getAllProducts(Pageable pageable);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.productId, p.name, p.brand, p.description, " +
            "p.status, p.quantity, p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice, p.imgUrl ) " +
            " FROM Product p" +
            " WHERE p.id = :id")
    Optional<ProductDTO> getProductDTOById(Long id);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.productId, p.name, p.brand, p.description, " +
            "p.status, p.quantity, p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice, p.imgUrl ) " +
            " FROM Product p" +
            " WHERE p.id = :productId")
    Optional<ProductDTO> getProductDTOByProductId(String productId);

    @Query("SELECT NEW com.ravi.orbit.dto.ProductDTO(p.id, p.productId, p.name, p.brand, p.description, " +
            "p.status, p.quantity, p.marketPrice, p.discountPercent, p.discountAmount, p.sellingPrice, p.imgUrl ) " +
            " FROM Product p" +
            " WHERE p.name = :name")
    List<ProductDTO> getProductDTOsByName(String name);

}
