package com.ravi.orbit.repository;

import com.ravi.orbit.entity.ProductSizeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeGroupRepository extends JpaRepository<ProductSizeGroup, Long> {

    ProductSizeGroup findByProductIdAndSizeGroupId(Long productId, Long sizeGroupId);

}
