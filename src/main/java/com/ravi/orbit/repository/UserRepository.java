package com.ravi.orbit.repository;

import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository <User, Long> {

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.id, u.firstName, u.middleName, u.lastName, " +
            "u.phone, u.email, u.username, u.imageUrl) " +
            "FROM User u " +
            "JOIN u.userRoles ur " +
            "JOIN ur.role r " +
            "WHERE r.role IN :roles " +
            "AND u.status = :status ")
    Page<UserDTO> getUsersByRolesAndStatus(Set<ERole> roles, EStatus status, Pageable pageable);

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.id, u.firstName, u.middleName, u.lastName, " +
            " u.phone, u.email, u.username, u.gender, u.dob, u.status, u.imageUrl, " +
            " u.address, u.zipcode, u.state, u.countryCode ) " +
            " FROM User u " +
            " WHERE u.id = :id ")
    Optional<UserDTO> getUserDTOById(Long id);

    @Query("SELECT NEW com.ravi.orbit.dto.UserDTO(u.id, u.firstName, u.middleName, u.lastName, " +
            " u.phone, u.email, u.username, u.gender, u.dob, u.status, u.imageUrl, " +
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
