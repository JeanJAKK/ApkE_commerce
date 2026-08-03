package com.ecommerce.controller;

import com.ecommerce.dto.request.*;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.AuthResponse;
import com.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour l'authentification
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "API d'authentification")
public class AuthController {

    private final AuthService authService;

    /**
     * Inscription d'un nouvel utilisateur
     */
    @PostMapping("/register")
    @Operation(summary = "Inscription", description = "Inscription d'un nouvel utilisateur")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Inscription réussie", response));
    }

    /**
     * Connexion d'un utilisateur
     */
    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Connexion d'un utilisateur")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Connexion réussie", response));
    }

    /**
     * Rafraîchir le token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Rafraîchir le token", description = "Obtenir un nouveau token d'accès")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Déconnexion
     */
    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Déconnecter l'utilisateur")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestParam Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok(ApiResponse.success("Déconnexion réussie", null));
    }

    /**
     * Mot de passe oublié
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Mot de passe oublié", description = "Demander la réinitialisation du mot de passe")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success(
            "Si un compte existe avec cet email, un lien de réinitialisation a été envoyé",
            null
        ));
    }

    /**
     * Réinitialiser le mot de passe
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Réinitialiser le mot de passe", description = "Réinitialiser le mot de passe avec un token")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Mot de passe réinitialisé avec succès", null));
    }
}
