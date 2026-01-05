package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.RefreshToken;
import com.ravi.orbit.entity.Role;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.entity.UserRoles;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.repository.RefreshTokenRepository;
import com.ravi.orbit.repository.RoleRepository;
import com.ravi.orbit.repository.UserRepository;
import com.ravi.orbit.repository.UserRolesRepository;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.utils.CommonMethods;
import com.ravi.orbit.utils.JwtUtil;
import com.ravi.orbit.utils.MyConstants;
import com.ravi.orbit.utils.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRolesRepository userRolesRepository;
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
    public AuthDTO signup(UserDTO userDTO, ERole role) {
        // Validate
        Validator.validateUserSignup(userDTO);

        // Create user entity
        User user = new User();
        mapToUserEntity(user, userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);

        Role roleDB = roleRepository.findByRole(role)
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + "Role" + role));

        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        userRoles.setRole(roleDB);

        userRolesRepository.save(userRoles);

        String accessToken = jwtUtil.generateJwtToken(user.getUsername(), role);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // Save refresh token
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(user.getUsername());
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        // Build response
        AuthDTO response = new AuthDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(null);
        response.setUserDTO(userDTO);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        log.info("User {} successfully registered with roles {}", user.getUsername(), role);
        return response;
    }

    public AuthDTO login(String username, String password, ERole requiredRole) {
        User user = getUserByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        Role role = roleRepository.findRoleByUsername(username)
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + "Role of user with username: " + username));

        if (!role.getRole().equals(requiredRole)) {
            throw new BadRequestException("User does not have required role");
        }

        // Generate JWTs with all roles
        String accessToken = jwtUtil.generateJwtToken(username, role.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(username);

        // Save refresh token
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(username);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        // Build response
        AuthDTO response = new AuthDTO();
        response.setUserDTO(getUserDTOByUsername(username));
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO, String username) {
        User user = getUserByUsername(username);
        mapToUserEntity(user, userDTO);
        userRepository.save(user);
        userDTO.setId(user.getId());
        userDTO.setPassword(null);
        return userDTO;
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

    // remaining to delete its children
    @Override
    public void deleteUserHard(Long userId) {
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

        return user;
    }

}
