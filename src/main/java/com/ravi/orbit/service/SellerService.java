package com.ravi.orbit.service;

import com.ravi.orbit.dto.SellerDto;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.exceptions.UserAlreadyExistsException;
import com.ravi.orbit.repository.SellerRepository;
import com.ravi.orbit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    public Seller findByUsername(String username) {
        return sellerRepository.findByUsername(username);
    }

    public Seller saveNewSeller(SellerDto sellerDto) {
        String username = sellerDto.getUsername();
        User userWithUsername = userRepository.findByUsername(username);
        Seller sellerWithUsername = sellerRepository.findByUsername(username);
        if (userWithUsername != null || sellerWithUsername != null){
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }

        String email = sellerDto.getEmail();
        User userWithEmail = userRepository.findByEmail(email);
        Seller sellerWithEmail = sellerRepository.findByEmail(email);
        if (userWithEmail != null ||  sellerWithEmail != null){
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        Seller seller = new Seller();
        seller.setFullName(sellerDto.getFullName());
        seller.setUsername(sellerDto.getUsername());
        seller.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
        seller.setEmail(sellerDto.getEmail());
        seller.setPhone(sellerDto.getPhone());
        seller.setBusinessDetails(sellerDto.getBusinessDetails());
        seller.setBankDetails(sellerDto.getBankDetails());
        seller.setPan(sellerDto.getPan());
        seller.setPickupAddress(sellerDto.getPickupAddress());

        // Save user to the database
        return sellerRepository.save(seller);

    }

    public Seller saveExistingSeller(String username,SellerDto sellerDto) {
        Seller existingSeller = sellerRepository.findByUsername(username);

        if (sellerDto.getFullName() != null) {
            existingSeller.setFullName(sellerDto.getFullName());
        }
        if (sellerDto.getUsername() != null) {
            existingSeller.setUsername(sellerDto.getUsername());
        }
        if (sellerDto.getPassword() != null) {
            existingSeller.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
        }
        if (sellerDto.getEmail() != null) {
            existingSeller.setEmail(sellerDto.getEmail());
        }
        if (sellerDto.getPhone() != null) {
            existingSeller.setPhone(sellerDto.getPhone());
        }
        if (sellerDto.getBusinessDetails() != null) {
            existingSeller.setBusinessDetails(sellerDto.getBusinessDetails());
        }
        if (sellerDto.getBankDetails() != null) {
            existingSeller.setBankDetails(sellerDto.getBankDetails());
        }
        if (sellerDto.getPan() != null) {
            existingSeller.setPan(sellerDto.getPan());
        }
        if (sellerDto.getPickupAddress() != null) {
            existingSeller.setPickupAddress(sellerDto.getPickupAddress());
        }

        return sellerRepository.save(existingSeller);

    }

//    public boolean deleteSellerById(Long id) {
//        if (sellerRepository.existsById(id)) {
//            sellerRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

}
