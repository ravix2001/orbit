package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.Role;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.entity.UserRoles;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.RoleRepository;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.repository.UserRolesRepository;
import com.ravi.orbit.service.IAdminService;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements IAdminService {

    private final IUserService userService;
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final RoleRepository roleRepository;

    @Override
    public String createAdmin(Long id) {

        User user = userService.getUserById(id);

        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + ERole.ROLE_ADMIN));

        boolean alreadyAdmin =
                userRolesRepository.existsByUserAndRole(user, adminRole);

        if (alreadyAdmin) {
            throw new BadRequestException(MyConstants.ERR_MSG_ALREADY_EXIST + "User as admin");
        }

        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(adminRole);

        userRolesRepository.save(userRoles);

        log.info("User with id {} granted ADMIN role", id);
        return user.getUsername() + " is now an admin";
    }

    @Override
    public String deleteAdmin(Long id) {

        User user = userService.getUserById(id);

        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + ERole.ROLE_ADMIN));

        UserRoles adminMapping =
                userRolesRepository.findByUserAndRole(user, adminRole)
                        .orElseThrow(() ->
                                new BadRequestException(MyConstants.ERR_MSG_BAD_REQUEST + "User is not an admin"));

        userRolesRepository.delete(adminMapping);

        // Safety: ensure user still has at least ROLE_USER
        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + ERole.ROLE_USER));

        boolean hasAnyRole =
                userRolesRepository.existsByUserAndRole(user, userRole);

        if (!hasAnyRole) {
            UserRoles fallback = new UserRoles();
            fallback.setUser(user);
            fallback.setRole(userRole);
            userRolesRepository.save(fallback);
        }

        log.info("User with id {} admin role revoked", id);
        return user.getUsername() + " is no longer an admin";
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.getUsersByRolesAndStatus(Set.of(ERole.ROLE_USER), EStatus.ACTIVE, pageable);
    }

    @Override
    public Page<UserDTO> getAllSellers(Pageable pageable) {
        return userRepository.getUsersByRolesAndStatus(Set.of(ERole.ROLE_SELLER), EStatus.ACTIVE, pageable);
    }

    @Override
    public Page<UserDTO> getAllAdmins(Pageable pageable) {
        return userRepository.getUsersByRolesAndStatus(Set.of(ERole.ROLE_ADMIN), EStatus.ACTIVE, pageable);
    }

}
