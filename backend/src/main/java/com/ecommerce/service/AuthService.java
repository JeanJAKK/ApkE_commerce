package com.ecommerce.service;

import com.ecommerce.dto.request.*;
import com.ecommerce.dto.response.AuthResponse;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.entity.ERole;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

/**
 * Service d'authentification
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    /**
     * Inscription d'un nouvel utilisateur
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Cet email est déjà utilisé");
        }

        if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Ce numéro de téléphone est déjà utilisé");
        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new ResourceNotFoundException("Rôle", "name", ERole.ROLE_USER));

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));
        user = userRepository.save(user);

        log.info("Nouvel utilisateur enregistré: {}", user.getEmail());

        return generateAuthResponse(user);
    }

    /**
     * Connexion d'un utilisateur
     */
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", request.getEmail()));

        if (user.isBlocked()) {
            throw new UnauthorizedException("Votre compte a été suspendu");
        }

        String accessToken = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(request.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        log.info("Connexion réussie: {}", user.getEmail());

        return generateAuthResponse(user, accessToken, refreshToken);
    }

    /**
     * Rafraîchir le token
     */
    public AuthResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("Token de rafraîchissement invalide");
        }

        String email = tokenProvider.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", email));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new UnauthorizedException("Token de rafraîchissement invalide");
        }

        String newAccessToken = tokenProvider.generateTokenFromEmail(email);
        String newRefreshToken = tokenProvider.generateRefreshToken(email);

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return generateAuthResponse(user, newAccessToken, newRefreshToken);
    }

    /**
     * Déconnexion
     */
    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        user.setRefreshToken(null);
        userRepository.save(user);

        log.info("Déconnexion: {}", user.getEmail());
    }

    /**
     * Mot de passe oublié
     */
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", request.getEmail()));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // TODO: Envoyer l'email avec le token
        log.info("Demande de mot de passe oublié pour: {}", user.getEmail());
    }

    /**
     * Réinitialiser le mot de passe
     */
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken())
            .orElseThrow(new BadRequestException("Token invalide ou expiré"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        userRepository.save(user);

        log.info("Mot de passe réinitialisé pour: {}", user.getEmail());
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = tokenProvider.generateTokenFromEmail(user.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return generateAuthResponse(user, accessToken, refreshToken);
    }

    private AuthResponse generateAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .tokenType("Bearer")
            .expiresIn(tokenProvider.getJwtExpiration())
            .user(userMapper.toResponse(user))
            .roles(user.getRoles().stream()
                .map(role -> role.getName().name())
                .toList())
            .build();
    }
}
