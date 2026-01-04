package com.ravi.orbit.repository;

import com.ravi.orbit.entity.Role;
import com.ravi.orbit.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(ERole role);

}
