package com.ravi.orbit.repository;

import com.ravi.orbit.dto.SellerDTO;
import com.ravi.orbit.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository <Seller, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.SellerDTO(s.id, s.firstName, s.middleName, s.lastName, s.phone, s.email, " +
            " s.username, s.gender, s.dob, s.role, s.status, s.imgURL, " +
            " s.address, s.zipcode, s.state, s.countryCode, s.citizenNumber, s.nid, s.pan ) " +
            " FROM Seller s " +
            " WHERE s.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    List<SellerDTO> getAllSellers();

    @Query("SELECT NEW com.ravi.orbit.dto.SellerDTO(s.id, s.firstName, s.middleName, s.lastName, s.phone, s.email, " +
            " s.username, s.gender, s.dob, s.role, s.status, s.imgURL, " +
            " s.address, s.zipcode, s.state, s.countryCode, s.citizenNumber, s.nid, s.pan ) " +
            " FROM Seller s " +
            " WHERE s.id = :id ")
    Optional<SellerDTO> getSellerDTOById(Long id);

    @Query("SELECT NEW com.ravi.orbit.dto.SellerDTO(s.id, s.firstName, s.middleName, s.lastName, s.phone, s.email, " +
            " s.username, s.gender, s.dob, s.role, s.status, s.imgURL, " +
            " s.address, s.zipcode, s.state, s.countryCode, s.citizenNumber, s.nid, s.pan ) " +
            " FROM Seller s " +
            " WHERE s.username = :username ")
    Optional<SellerDTO> getSellerDTOByUsername(String username);

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(s.username, s.password) " +
            " FROM Seller s " +
            " WHERE s.username = :username ")
    Optional<SellerDTO> getAuthByUsername(String username);

    Optional<Seller> findByUsername(String username);

    Optional<Seller> findByEmail(String email);

}
