package com.ravi.orbit.repository;

import com.ravi.orbit.dto.SizeDTO;
import com.ravi.orbit.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.SizeDTO(s.id, s.size, s.isAvailable, s.price, s.quantity, s.productId)" +
            " FROM Size s" +
            " WHERE s.productId = :productId ")
    List<SizeDTO> getSizeDTOsByProductId(Long productId);

}
