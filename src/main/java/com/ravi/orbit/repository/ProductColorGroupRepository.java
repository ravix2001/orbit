package com.ravi.orbit.repository;

import com.ravi.orbit.entity.ProductColorGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductColorGroupRepository extends JpaRepository<ProductColorGroup, Long> {

    ProductColorGroup findByProductIdAndColorGroupId(Long productId, Long colorGroupId);

}
