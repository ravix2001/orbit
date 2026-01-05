package com.ravi.orbit.service.impl;

import com.ravi.orbit.entity.Role;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.RoleRepository;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );

        // SINGLE ROLE PER USER
        Role role = roleRepository.findRoleByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + "Role of user with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(String.valueOf(role.getRole())) // IMPORTANT
                .build();
    }
}
