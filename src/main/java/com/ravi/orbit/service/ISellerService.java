package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISellerService {

    AuthDTO sellerSignup(SellerDTO sellerDTO);

    AuthDTO sellerLogin(SellerDTO sellerDTO);

    SellerDTO updateSeller(SellerDTO sellerDTO, String username);

    Page<SellerDTO> getAllSellers(Pageable pageable);

    SellerDTO getSellerDTOById(Long sellerId);

    SellerDTO getSellerDTOByUsername(String username);

    SellerDTO getSellerAuthByUsername(String username);

    void deleteSeller(String username);

    void deleteSellerHard(Long id);

    Seller getSellerById(Long sellerId);

    Seller getSellerByUsername(String username);

    Seller getSellerByEmail(String email);

}
