package com.ravi.orbit.service;

import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.repository.SellerRepository;
import com.ravi.orbit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("Attempting to load user: {}", username);

        User user = userRepository.findByUsername(username);

        if (user != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole().toString())
                    .build();
        }

        Seller seller = sellerRepository.findByUsername(username);

        if (seller != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(seller.getUsername())
                    .password(seller.getPassword())
                    .authorities(seller.getRole().toString())
                    .build();
        }

        // If neither found, throw an exception
        log.warn("User not found: {}", username);
        throw new UsernameNotFoundException("User not found with username: " + username);

    }

}
