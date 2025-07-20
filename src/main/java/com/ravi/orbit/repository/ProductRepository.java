package com.ravi.orbit.repository;

import com.ravi.orbit.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByProductId(String productId);

    List<Product> findByName(String name);

//    The _ (underscore) lets Spring Data JPA traverse into the category field and use its id.
    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findBySeller_Username(String username);

}
