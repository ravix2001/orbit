package com.ravi.orbit.service.impl;

import com.ravi.orbit.dto.AuthDTO;
import com.ravi.orbit.dto.UserDTO;
import com.ravi.orbit.entity.RefreshToken;
import com.ravi.orbit.entity.Role;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.exceptions.InvalidTokenException;
import com.ravi.orbit.repository.RefreshTokenRepository;
import com.ravi.orbit.repository.RoleRepository;
import com.ravi.orbit.service.IAuthService;
import com.ravi.orbit.service.IUserService;
import com.ravi.orbit.utils.JwtUtil;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public AuthDTO userSignup(UserDTO userDTO) {
        return userService.signup(userDTO, ERole.ROLE_USER);
    }

    @Override
    public AuthDTO sellerSignup(UserDTO userDTO) {
        return userService.signup(userDTO, ERole.ROLE_SELLER);
    }

    @Override
    public AuthDTO userLogin(AuthDTO authDTO) {
        return userService.login(authDTO.getUsername(), authDTO.getPassword(), ERole.ROLE_USER);
    }

    @Override
    public AuthDTO sellerLogin(AuthDTO authDTO) {
        return userService.login(authDTO.getUsername(), authDTO.getPassword(), ERole.ROLE_SELLER);
    }

    @Override
    public AuthDTO adminLogin(AuthDTO authDTO) {
        return userService.login(authDTO.getUsername(), authDTO.getPassword(), ERole.ROLE_ADMIN);
    }

    @Override
    public Map<String, String> refreshToken(String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        // Validate refresh token existence
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new BadRequestException(
                                MyConstants.ERR_MSG_NOT_FOUND + "Refresh Token"))
                ;

        // Validate expiry
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidTokenException("Refresh token expired");
        }

        // Validate token type
        if (!jwtUtil.isRefreshToken(token)) {
            throw new InvalidTokenException("Invalid token type");
        }

        // Load user + roles
        User user = userService.getUserByUsername(refreshToken.getUsername());

        Role role = roleRepository.findRoleByUsername(refreshToken.getUsername())
                .orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + "Role of user with username: " + refreshToken.getUsername()));


        // Generate new access token WITH ROLES
        String newAccessToken =
                jwtUtil.generateJwtToken(user.getUsername(), role.getRole());

        return Map.of("accessToken", newAccessToken);
    }


    public Map<String, String> logout(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        RefreshToken refreshToken = getRefreshToken(token);
        refreshTokenRepository.delete(refreshToken);
        return Map.of("message", "Logged out successfully");
    }

    public RefreshToken getRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new BadRequestException(MyConstants.ERR_MSG_NOT_FOUND + "Refresh Token: " + token));
    }

}
