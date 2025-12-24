package com.ravi.orbit.service;

import com.ravi.orbit.dto.AuthPayload;
import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.entity.Seller;

import java.util.List;

public interface ISellerService {

    AuthPayload sellerSignup(SellerDTO sellerDTO);

    AuthPayload sellerLogin(SellerDTO sellerDTO);

    List<SellerDTO> getAllSellers();

    SellerDTO getSellerDTOById(Long sellerId);

    SellerDTO getSellerDTOByUsername(String username);

    SellerDTO getSellerAuthByUsername(String username);

    void deleteSeller(String username);

    void deleteSellerHard(Long id);

    Seller getSellerById(Long sellerId);

    Seller getSellerByUsername(String username);

    Seller getSellerByEmail(String email);

}
