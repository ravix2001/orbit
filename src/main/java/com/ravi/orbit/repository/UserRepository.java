package com.ravi.orbit.repository;

import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.id, u.firstName, u.middleName, u.lastName, u.phone, u.email, " +
            " u.username, u.gender, u.dob, u.role, u.status, u.imgURL, " +
            " u.address, u.zipcode, u.state, u.countryCode ) " +
            " FROM User u " +
            "WHERE u.status = com.ravi.orbit.enums.EStatus.ACTIVE ")
    Page<UserDTO> getAllUsers(Pageable pageable);

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.id, u.firstName, u.middleName, u.lastName, u.phone, u.email, " +
            " u.username, u.gender, u.dob, u.role, u.status, u.imgURL, " +
            " u.address, u.zipcode, u.state, u.countryCode ) " +
            " FROM User u " +
            " WHERE u.id = :id ")
    Optional<UserDTO> getUserDTOById(Long id);

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.id, u.firstName, u.middleName, u.lastName, u.phone, u.email, " +
            " u.username, u.gender, u.dob, u.role, u.status, u.imgURL, " +
            " u.address, u.zipcode, u.state, u.countryCode ) " +
            " FROM User u " +
            " WHERE u.username = :username ")
    Optional<UserDTO> getUserDTOByUsername(String username);

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.username, u.password) " +
            " FROM User u " +
            " WHERE u.username = :username ")
    Optional<UserDTO> getAuthByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
