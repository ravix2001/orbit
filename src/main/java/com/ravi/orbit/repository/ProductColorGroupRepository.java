package com.ravi.orbit.repository;

import com.ravi.orbit.entity.ProductColorGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductColorGroupRepository extends JpaRepository<ProductColorGroup, Long> {


    Optional<ProductColorGroup> findByProductId(Long productId);

    Optional<ProductColorGroup> findByProductIdAndColorGroupId(Long productId, Long colorGroupId);

}
