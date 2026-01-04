package com.ravi.orbit.repository;

import com.ravi.orbit.entity.Role;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    boolean existsByUserAndRole(User user, Role role);

    Optional<UserRoles> findByUserAndRole(User user, Role role);

    List<UserRoles> findByUser(User user);

}
