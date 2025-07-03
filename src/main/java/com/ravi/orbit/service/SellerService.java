package com.ravi.orbit.service;

import com.ravi.orbit.dto.SellerDto;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    public Seller setSeller(SellerDto sellerDto) {
        Seller seller = new Seller();
        seller.setFullName(sellerDto.getFullName());
        seller.setUsername(sellerDto.getUsername());
        seller.setPassword(sellerDto.getPassword());
        seller.setEmail(sellerDto.getEmail());
        seller.setPhone(sellerDto.getPhone());
        seller.setBusinessDetails(sellerDto.getBusinessDetails());
        seller.setBankDetails(sellerDto.getBankDetails());
        seller.setPan(sellerDto.getPan());
        seller.setPickupAddress(sellerDto.getPickupAddress());

        return sellerRepository.save(seller);

    }

    public boolean deleteSellerById(Long id) {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
