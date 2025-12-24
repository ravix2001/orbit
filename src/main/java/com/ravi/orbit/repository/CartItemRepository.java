package com.ravi.orbit.repository;

import com.ravi.orbit.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


}
