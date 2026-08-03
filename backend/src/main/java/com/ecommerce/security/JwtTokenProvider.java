package com.ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Composant pour la génération et la validation des tokens JWT
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        if (keyBytes.length < 32) {
            keyBytes = jwtSecret.getBytes();
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return generateTokenFromEmail(userPrincipal.getUsername());
    }

    public String generateTokenFromEmail(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
            .subject(email)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact();
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpiration);

        return Jwts.builder()
            .subject(email)
            .issuedAt(now)
            .expiration(expiryDate)
            .claim("type", "refresh")
            .signWith(key)
            .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Token JWT invalide");
        } catch (ExpiredJwtException ex) {
            log.error("Token JWT expiré");
        } catch (UnsupportedJwtException ex) {
            log.error("Token JWT non supporté");
        } catch (IllegalArgumentException ex) {
            log.error("Claims JWT vide");
        }
        return false;
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }
}
