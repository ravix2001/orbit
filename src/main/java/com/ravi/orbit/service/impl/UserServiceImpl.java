package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.RefreshToken;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.RefreshTokenRepository;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
    public AuthDTO userSignup(UserDTO userDTO) {
        Validator.validateUserSignup(userDTO);
        AuthDTO response = new AuthDTO();

        User user = new User();

        userRepository.save(mapToUserEntity(user, userDTO));
        userDTO.setId(user.getId());
        userDTO.setPassword(null);
        response.setUserDTO(userDTO);
        
        String accessToken = jwtUtil.generateJwtToken(userDTO.getPhone());
        String refreshToken = jwtUtil.generateRefreshToken(userDTO.getPhone());

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(userDTO.getUsername());
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

    @Override
    public AuthDTO userLogin(UserDTO userDTO) {

        UserDTO auth = getUserAuthByUsername(userDTO.getUsername());

        if (!passwordEncoder.matches(userDTO.getPassword(), auth.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        AuthDTO response = new AuthDTO();

        String accessToken = jwtUtil.generateJwtToken(auth.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(auth.getUsername());

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(userDTO.getUsername());
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUserDTO(getUserDTOByUsername(auth.getUsername()));

        log.info("User {} successfully logged in", auth.getUsername());

        return response;
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

}
