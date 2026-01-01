package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.entity.RefreshToken;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.RefreshTokenRepository;
import com.ravi.orbit.repository.SellerRepository;
import com.ravi.orbit.service.ISellerService;
import com.ravi.orbit.utils.JwtUtil;
import com.ravi.orbit.utils.MyConstants;
import com.ravi.orbit.utils.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements ISellerService {

    private final SellerRepository sellerRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthDTO sellerSignup(SellerDTO sellerDTO) {
        Validator.validateSellerSignup(sellerDTO);
        AuthDTO response = new AuthDTO();

        Seller seller = new Seller();

        sellerRepository.save(mapToSellerEntity(seller, sellerDTO));

        sellerDTO.setId(seller.getId());
        response.setSellerDTO(sellerDTO);

        String accessToken = jwtUtil.generateJwtToken(sellerDTO.getPhone());
        String refreshToken = jwtUtil.generateRefreshToken(sellerDTO.getPhone());

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(sellerDTO.getUsername());
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public AuthDTO sellerLogin(SellerDTO sellerDTO) {

        SellerDTO auth = getSellerAuthByUsername(sellerDTO.getUsername());

        if (!passwordEncoder.matches(sellerDTO.getPassword(), auth.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        AuthDTO response = new AuthDTO();

        String accessToken = jwtUtil.generateJwtToken(auth.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(auth.getUsername());

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(sellerDTO.getUsername());
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setSellerDTO(getSellerDTOByUsername(auth.getUsername()));

        log.info("Seller {} successfully logged in", auth.getUsername());

        return response;
    }


    @Override
    public SellerDTO updateSeller(SellerDTO sellerDTO, String username) {
        Seller seller = getSellerByUsername(username);
        sellerRepository.save(mapToSellerEntity(seller, sellerDTO));
        return sellerDTO;
    }

    @Override
    public Page<SellerDTO> getAllSellers(Pageable pageable) {
        return sellerRepository.getAllSellers(pageable);
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
        seller.setImageUrl(sellerDTO.getImageUrl());
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

}
