package com.ravi.orbit.service.impl;

import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.service.IAdminService;
import com.ravi.orbit.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements IAdminService {

    private final IUserService userService;
    private final UserRepository userRepository;

    @Override
    public String createAdmin(Long id){
        User user = userService.getUserById(id);
        user.setRole(ERole.ROLE_ADMIN);
        userRepository.save(user);
        log.info("User with id {} is added as admin", id);
        return user.getUsername() + " is added as admin";
    }

    @Override
    public String deleteAdmin(Long id){
        User user = userService.getUserById(id);
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
        log.error("User with id {} is removed from admin", id);
        return user.getUsername() + " is removed from admin";
    }

}
