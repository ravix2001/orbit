package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.AuthPayload;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.JwtUtil;
import com.ravi.orbit.utils.MyConstants;
import com.ravi.orbit.utils.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // handle user for admin controller
    @Override
    public UserDTO handleUser(UserDTO userDTO) {

        Validator.validateUserSignup(userDTO);

        User user = null;

        if(CommonMethods.isEmpty(userDTO.getId())){
            user = new User();
        }
        else{
            user = getUserById(userDTO.getId());
        }

        userRepository.save(mapToUserEntity(user, userDTO));

        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public AuthPayload userSignup(UserDTO userDTO) {
        Validator.validateUserSignup(userDTO);
        AuthPayload response = new AuthPayload();

        User user = new User();

        userRepository.save(mapToUserEntity(user, userDTO));
        userDTO.setId(user.getId());
        userDTO.setPassword(null);
        response.setUserDTO(userDTO);
        
        String accessToken = jwtUtil.generateJwtToken(userDTO.getPhone());
        String refreshToken = jwtUtil.generateRefreshToken(userDTO.getPhone());

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public AuthPayload userLoginNew(UserDTO loginRequest) {
        AuthPayload response = new AuthPayload();
        UserDTO auth = getUserAuthByUsername(loginRequest.getUsername());

        if(passwordEncoder.matches(loginRequest.getPassword(), auth.getPassword())){
            String accessToken = jwtUtil.generateJwtToken(loginRequest.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
        }

        log.info("User {} successfully logged in", auth.getUsername());

        response.setUserDTO(getUserDTOByUsername(auth.getUsername()));
        return response;
    }

    @Override
    public AuthPayload userLoginOld(UserDTO userDTO) {
        try {
            AuthPayload response = new AuthPayload();

            // Create a user object and set credentials from the login DTO
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());

            // Authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            // Load user details from the UserDetailsService implementation
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());

            String accessToken = jwtUtil.generateJwtToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setUserDTO(mapToUserDTO(userDTO, user));

            log.info("User {} successfully logged in", user.getUsername());

            return response;
        } catch (Exception e) {
            log.error("Error logging in user: {}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String username) {
        User user = getUserByUsername(username);
        userRepository.save(mapToUserEntity(user, userDTO));
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.getAllUsers(pageable);
    }

    @Override
    public UserDTO getUserDTOById(Long id) {
        return userRepository.getUserDTOById(id)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User: " + id));
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        return userRepository.getUserDTOByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User: " + username));
    }

    @Override
    public UserDTO getUserAuthByUsername(String username) {
        return userRepository.getAuthByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User: " + username));
    }

    @Override
    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        user.setStatus(EStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public void deleteUserHard(Long userId) {       // remaining to delete its children
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User: " + userId));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User: " + username));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(MyConstants
                        .ERR_MSG_NOT_FOUND + "User: " + email));
    }

    public User mapToUserEntity (User user, UserDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getPhone());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setDob(userDTO.getDob());
        user.setImageUrl(userDTO.getImageUrl());
        // address
        user.setAddress(userDTO.getAddress());
        user.setZipcode(userDTO.getZipcode());
        user.setState(userDTO.getState());
        user.setCountryCode(userDTO.getCountryCode());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return user;
    }

    public UserDTO mapToUserDTO (UserDTO userDTO, User user) {
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getPhone());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        userDTO.setGender(user.getGender());
        userDTO.setDob(user.getDob());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        userDTO.setImageUrl(user.getImageUrl());
        // address
        userDTO.setAddress(user.getAddress());
        userDTO.setZipcode(user.getZipcode());
        userDTO.setState(user.getState());
        userDTO.setCountryCode(user.getCountryCode());
        userDTO.setPassword(passwordEncoder.encode(user.getPassword()));

        return userDTO;
    }

}
