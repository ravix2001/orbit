package com.ravi.orbit.service.impl;

import com.ravi.orbit.entity.RefreshToken;
import com.ravi.orbit.exceptions.BadRequestException;
import com.ravi.orbit.exceptions.InvalidTokenException;
import com.ravi.orbit.repository.RefreshTokenRepository;
import com.ravi.orbit.service.IAuthService;
import com.ravi.orbit.utils.JwtUtil;
import com.ravi.orbit.utils.MyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public Map<String, String> refreshToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        RefreshToken refreshToken = getRefreshToken(token);

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidTokenException("Refresh token expired");
        }

        if (!jwtUtil.isRefreshToken(token)) {
            throw new InvalidTokenException("Invalid token type");
        }

        String newAccessToken =
                jwtUtil.generateJwtToken(refreshToken.getUsername());

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
