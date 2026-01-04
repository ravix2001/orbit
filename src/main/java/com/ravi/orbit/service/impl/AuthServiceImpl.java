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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements IAuthService {

    private final IUserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthDTO userSignup(UserDTO userDTO) {
        return userService.signup(userDTO, Set.of(ERole.ROLE_USER));
    }

    @Override
    public AuthDTO sellerSignup(UserDTO userDTO) {
        return userService.signup(userDTO, Set.of(ERole.ROLE_SELLER));
    }

    @Override
    public AuthDTO login(String username, String password, Set<ERole> requiredRoles) {
        User user = userService.getUserByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        // Verify user has at least one required role
        Set<ERole> userRoles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());
        if (userRoles.stream().noneMatch(requiredRoles::contains)) {
            throw new BadRequestException("User does not have required role");
        }

        // Generate JWTs with all roles
        Set<String> roleNames = userRoles.stream().map(Enum::name).collect(Collectors.toSet());
        String accessToken = jwtUtil.generateJwtToken(username, roleNames);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        // Save refresh token
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(username);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshTokenEntity);

        // Build response
        AuthDTO response = new AuthDTO();
        response.setUserDTO(userService.getUserDTOByUsername(username));
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
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

        Set<String> roleNames = user.getRoles()
                .stream()
                .map(r -> r.getRole().name())
                .collect(Collectors.toSet());

        // Generate new access token WITH ROLES
        String newAccessToken =
                jwtUtil.generateJwtToken(user.getUsername(), roleNames);

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
