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

        // Fetch the user's current role
        UserRoles currentRole = userRolesRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User has no role assigned"));

        // Check if the user is already admin
        if (currentRole.getRole().getRole() == ERole.ROLE_ADMIN) {
            throw new BadRequestException(MyConstants
                    .ERR_MSG_ALREADY_EXIST + " User as admin");
        }

        // If the user has USER role, delete it
        if (currentRole.getRole().getRole() == ERole.ROLE_USER) {
            userRolesRepository.delete(currentRole);
        }

        // Assign ADMIN role
        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + " " + ERole.ROLE_ADMIN));

        UserRoles adminUserRoles = new UserRoles();
        adminUserRoles.setUser(user);
        adminUserRoles.setRole(adminRole);

        userRolesRepository.save(adminUserRoles);

        log.info("User with id {} granted ADMIN role", id);
        return user.getUsername() + " is added as admin";
    }


    @Override
    public String deleteAdmin(Long id) {
        User user = userService.getUserById(id);

        // Fetch the user's current role
        UserRoles currentRole = userRolesRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User has no role assigned"));

        // If the user is already a regular user, throw error
        if (currentRole.getRole().getRole() == ERole.ROLE_USER) {
            throw new BadRequestException(MyConstants
                    .ERR_MSG_ALREADY_EXIST + " User is already a regular user");
        }

        // If the user has ADMIN role, remove it
        if (currentRole.getRole().getRole() == ERole.ROLE_ADMIN) {
            userRolesRepository.delete(currentRole);
        }

        // Assign USER role
        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + " " + ERole.ROLE_USER));

        UserRoles newUserRole = new UserRoles();
        newUserRole.setUser(user);
        newUserRole.setRole(userRole);

        userRolesRepository.save(newUserRole);

        log.info("User with id {} admin role revoked", id);
        return user.getUsername() + " is no longer an admin";
    }

    @Override
    public Page<UserDTO> getUsersByRoleAndStatus(ERole role, EStatus status, Pageable pageable) {
        return userRepository.getUsersByRoleAndStatus(role, status, pageable);
    }

    // for multiple roles
//    @Override
//    public String deleteAdmin(Long id) {
//
//        User user = userService.getUserById(id);
//
//        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
//                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + ERole.ROLE_ADMIN));
//
//        UserRoles adminMapping =
//                userRolesRepository.findByUserAndRole(user, adminRole)
//                        .orElseThrow(() ->
//                                new BadRequestException(MyConstants.ERR_MSG_BAD_REQUEST + "User is not an admin"));
//
//        userRolesRepository.delete(adminMapping);
//
//        // Safety: ensure user still has at least ROLE_USER
//        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
//                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + ERole.ROLE_USER));
//
//        boolean hasAnyRole =
//                userRolesRepository.existsByUserAndRole(user, userRole);
//
//        if (!hasAnyRole) {
//            UserRoles fallback = new UserRoles();
//            fallback.setUser(user);
//            fallback.setRole(userRole);
//            userRolesRepository.save(fallback);
//        }
//
//        log.info("User with id {} admin role revoked", id);
//        return user.getUsername() + " is no longer an admin";
//    }

//    @Override
//    public Page<UserDTO> getAllSellers(Pageable pageable) {
//        return userRepository.getUsersByRoleAndStatus(ERole.ROLE_SELLER, EStatus.ACTIVE, pageable);
//    }
//
//    @Override
//    public Page<UserDTO> getAllAdmins(Pageable pageable) {
//        return userRepository.getUsersByRoleAndStatus(ERole.ROLE_ADMIN, EStatus.ACTIVE, pageable);
//    }

}
