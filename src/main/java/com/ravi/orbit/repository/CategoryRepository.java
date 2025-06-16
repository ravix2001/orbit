package com.ravi.orbit.repository;

import com.ravi.orbit.entity.Category;
import com.ravi.orbit.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
