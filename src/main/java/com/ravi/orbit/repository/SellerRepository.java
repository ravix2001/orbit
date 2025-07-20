package com.ravi.orbit.repository;

import com.ravi.orbit.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository <Seller, Long> {

    Seller findByUsername(String username);

    Seller findByEmail(String email);

    void deleteByUsername(String username);

}
