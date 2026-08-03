package com.ecommerce.controller;

import com.ecommerce.dto.request.UpdateUserRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller pour la gestion des utilisateurs
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
public class UserController {

    private final UserService userService;

    /**
     * Récupérer un utilisateur par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur", description = "Récupérer un utilisateur par son ID")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable Long id) {
        UserResponse response = userService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Mettre à jour un utilisateur
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un utilisateur", description = "Mettre à jour un utilisateur")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur mis à jour avec succès", response));
    }

    /**
     * Bloquer un utilisateur (Admin)
     */
    @PostMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Bloquer un utilisateur", description = "Bloquer un utilisateur")
    public ResponseEntity<ApiResponse<Void>> block(@PathVariable Long id) {
        userService.block(id);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur bloqué avec succès", null));
    }

    /**
     * Débloquer un utilisateur (Admin)
     */
    @PostMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Débloquer un utilisateur", description = "Débloquer un utilisateur")
    public ResponseEntity<ApiResponse<Void>> unblock(@PathVariable Long id) {
        userService.unblock(id);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur débloqué avec succès", null));
    }

    /**
     * Supprimer un utilisateur (Admin)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprimer un utilisateur")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur supprimé avec succès", null));
    }

    /**
     * Rechercher des utilisateurs (Admin)
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Rechercher", description = "Rechercher des utilisateurs")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<UserResponse> response = userService.search(query, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer tous les utilisateurs (Admin)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Tous les utilisateurs", description = "Récupérer tous les utilisateurs")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<UserResponse> response = userService.getAll(page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les utilisateurs bloqués (Admin)
     */
    @GetMapping("/blocked")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Utilisateurs bloqués", description = "Récupérer les utilisateurs bloqués")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getBlockedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<UserResponse> response = userService.getBlockedUsers(page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Obtenir l'historique des commandes d'un utilisateur
     */
    @GetMapping("/{id}/orders")
    @Operation(summary = "Historique des commandes", description = "Récupérer l'historique des commandes")
    public ResponseEntity<ApiResponse<List<Object[]>>> getOrderHistory(@PathVariable Long id) {
        List<Object[]> response = userService.getOrderHistory(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Obtenir le montant total dépensé
     */
    @GetMapping("/{id}/total-spent")
    @Operation(summary = "Montant dépensé", description = "Récupérer le montant total dépensé")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalSpent(@PathVariable Long id) {
        BigDecimal response = userService.getTotalSpent(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
