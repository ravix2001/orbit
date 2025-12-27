package com.ravi.orbit.repository;

import com.ravi.orbit.dto.CategoryDTO;
import com.ravi.orbit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.CategoryDTO(c.id, c.name, c.status, c.imageUrl) " +
            " FROM Category c" +
            " WHERE c.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<CategoryDTO> getAllCategories(Pageable pageable);

    @Query("SELECT NEW com.ravi.orbit.dto.CategoryDTO(c.id, c.name, c.status, c.imageUrl) " +
            " FROM Category c" +
            " WHERE c.id = :id")
    Optional<CategoryDTO> getCategoryDTOById(Long id);

}
