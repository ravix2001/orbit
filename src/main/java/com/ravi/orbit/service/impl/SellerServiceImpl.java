package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.AuthPayload;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.SellerRepository;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.utils.JwtUtil;
import com.ravi.orbit.utils.MyConstants;
import com.ravi.orbit.utils.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements ISellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthPayload sellerSignup(SellerDTO sellerDTO) {
        Validator.validateSellerSignup(sellerDTO);
        AuthPayload response = new AuthPayload();

        Seller seller = new Seller();

        sellerRepository.save(mapToSellerEntity(seller, sellerDTO));

        sellerDTO.setId(seller.getId());
        response.setSellerDTO(sellerDTO);

        String accessToken = jwtUtil.generateJwtToken(sellerDTO.getPhone());
        String refreshToken = jwtUtil.generateRefreshToken(sellerDTO.getPhone());

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public AuthPayload sellerLogin(SellerDTO sellerDTO) {
        AuthPayload response = new AuthPayload();
        SellerDTO auth = getSellerAuthByUsername(sellerDTO.getUsername());

        if(passwordEncoder.matches(sellerDTO.getPassword(), auth.getPassword())){
            String accessToken = jwtUtil.generateJwtToken(sellerDTO.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(sellerDTO.getUsername());
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
        }

        log.info("Seller {} successfully logged in", auth.getUsername());

        response.setSellerDTO(getSellerDTOByUsername(auth.getUsername()));
        return response;
    }

    @Override
    public List<SellerDTO> getAllSellers() {
        return sellerRepository.getAllSellers();
    }

    @Override
    public SellerDTO getSellerDTOById(Long sellerId) {
        return sellerRepository.getSellerDTOById(sellerId)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Seller: " + sellerId));
    }

    @Override
    public SellerDTO getSellerDTOByUsername(String username) {
        return sellerRepository.getSellerDTOByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Seller: " + username));
    }

    @Override
    public SellerDTO getSellerAuthByUsername(String username) {
        return sellerRepository.getAuthByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Seller: " + username));
    }

    @Override
    public void deleteSeller(String username) {
        Seller seller = getSellerByUsername(username);
        seller.setStatus(EStatus.DELETED);
        sellerRepository.save(seller);
    }

    @Override
    public void deleteSellerHard(Long sellerId) {
        sellerRepository.deleteById(sellerId);
    }

    @Override
    public Seller getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Seller: " + sellerId));
    }

    @Override
    public Seller getSellerByUsername(String username) {
        return sellerRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Seller: " + username));
    }

    @Override
    public Seller getSellerByEmail(String email) {
        return sellerRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "Seller: " + email));
    }

    public Seller mapToSellerEntity (Seller seller, SellerDTO sellerDTO) {
        seller.setFirstName(sellerDTO.getFirstName());
        seller.setMiddleName(sellerDTO.getMiddleName());
        seller.setLastName(sellerDTO.getLastName());
        seller.setUsername(sellerDTO.getPhone());
        seller.setPhone(sellerDTO.getPhone());
        seller.setEmail(sellerDTO.getEmail());
        seller.setGender(sellerDTO.getGender());
        seller.setDob(sellerDTO.getDob());
        seller.setImgURL(sellerDTO.getImgURL());
        // address
        seller.setAddress(sellerDTO.getAddress());
        seller.setZipcode(sellerDTO.getZipcode());
        seller.setState(sellerDTO.getState());
        seller.setCountryCode(sellerDTO.getCountryCode());
        seller.setCitizenNumber(sellerDTO.getCitizenNumber());
        seller.setNid(sellerDTO.getNid());
        seller.setPan(sellerDTO.getPan());
        seller.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));

        return seller;
    }

    public SellerDTO mapToSellerDTO (SellerDTO sellerDTO, Seller seller) {
        sellerDTO.setFirstName(seller.getFirstName());
        sellerDTO.setMiddleName(seller.getMiddleName());
        sellerDTO.setLastName(seller.getLastName());
        sellerDTO.setUsername(seller.getPhone());
        sellerDTO.setPhone(seller.getPhone());
        sellerDTO.setEmail(seller.getEmail());
        sellerDTO.setGender(seller.getGender());
        sellerDTO.setDob(seller.getDob());
        sellerDTO.setRole(seller.getRole());
        sellerDTO.setStatus(seller.getStatus());
        sellerDTO.setImgURL(seller.getImgURL());
        // address
        sellerDTO.setAddress(seller.getAddress());
        sellerDTO.setZipcode(seller.getZipcode());
        sellerDTO.setState(seller.getState());
        sellerDTO.setCountryCode(seller.getCountryCode());
        sellerDTO.setPassword(passwordEncoder.encode(seller.getPassword()));

        return sellerDTO;
    }

}
