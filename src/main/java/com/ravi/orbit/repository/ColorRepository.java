package com.ravi.orbit.repository;

import com.ravi.orbit.dto.ColorDTO;
import com.ravi.orbit.dto.SizeDTO;
import com.ravi.orbit.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.ColorDTO(c.id, c.color, c.isAvailable)" +
            " FROM Color c" +
            " WHERE c.colorGroupId = :colorGroupId ")
    List<ColorDTO> getColorDTOsByColorGroupId(Long colorGroupId);

}
