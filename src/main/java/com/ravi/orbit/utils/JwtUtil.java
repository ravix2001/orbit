package com.ravi.orbit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Value( "${jwt.secret.key}")
    private String secretKey;

    private static final long JWT_TOKEN_VALIDITY = (long) 1000 * 60 * 15 ;      // expiration time = 15 minutes

    private static final long REFRESH_TOKEN_VALIDITY = (long) 1000 * 60 * 60 * 24 * 7;    // 7 Days in milliseconds


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String createToken(Map<String, Object> claims, String subject, long expirationMillis){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateJwtToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return createToken(claims, username, JWT_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return createToken(claims, username, REFRESH_TOKEN_VALIDITY);
    }

    public Boolean validateToken(String token){
        try {
            String type = extractAllClaims(token).get("type").toString();
            if (!type.equals("access")) {
                throw new IllegalArgumentException("Invalid token type");
            }

            return !isTokenExpired(token);

        }catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
//            throw new IllegalArgumentException("Token expired");
            return false;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }

    }
}
