package com.ravi.orbit.repository;

import com.ravi.orbit.dto.ColorDTO;
import com.ravi.orbit.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.ColorDTO(c.id, c.color, c.isAvailable, c.price, c.quantity, c.productId)" +
            " FROM Color c" +
            " WHERE c.productId = :productId ")
    List<ColorDTO> getColorDTOsByProductId(Long productId);

}
