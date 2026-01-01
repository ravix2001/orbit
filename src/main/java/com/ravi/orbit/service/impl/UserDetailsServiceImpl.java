package com.ravi.orbit.service.impl;

import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.SellerRepository;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Attempting to load user/seller: {}", username);

        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Seller> sellerOptional = sellerRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            log.info("Attempting to load user: {}", username);
            User user = userOptional.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole().toString())
                    .build();
        }
        else if (sellerOptional.isPresent()) {
            log.info("Attempting to load seller: {}", username);
            Seller seller = sellerOptional.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(seller.getUsername())
                    .password(seller.getPassword())
                    .authorities(seller.getRole().toString())
                    .build();
        }

        // If neither found, throw an exception
        log.warn("User not found: {}", username);
        throw new BadRequestException(MyConstants
                .ERR_MSG_NOT_FOUND + "User: " + username);

    }

//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new BadRequestException(MyConstants
//                        .ERR_MSG_NOT_FOUND + "User: " + username));
//    }
//
//    public Seller getSellerByUsername(String username) {
//        return sellerRepository.findByUsername(username)
//                .orElseThrow(() -> new BadRequestException(MyConstants
//                        .ERR_MSG_NOT_FOUND + "Seller: " + username));
//    }

}
