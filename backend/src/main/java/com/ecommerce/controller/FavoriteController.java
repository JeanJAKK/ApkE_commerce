package com.ecommerce.controller;

import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour les favoris
 */
@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Favoris", description = "API de gestion des favoris")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * Ajouter aux favoris
     */
    @PostMapping
    @Operation(summary = "Ajouter aux favoris", description = "Ajouter un produit aux favoris")
    public ResponseEntity<ApiResponse<Void>> addFavorite(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        boolean added = favoriteService.addFavorite(userId, productId);
        if (added) {
            return ResponseEntity.ok(ApiResponse.success("Produit ajouté aux favoris", null));
        } else {
            return ResponseEntity.ok(ApiResponse.success("Produit déjà en favoris", null));
        }
    }

    /**
     * Retirer des favoris
     */
    @DeleteMapping
    @Operation(summary = "Retirer des favoris", description = "Retirer un produit des favoris")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        boolean removed = favoriteService.removeFavorite(userId, productId);
        if (removed) {
            return ResponseEntity.ok(ApiResponse.success("Produit retiré des favoris", null));
        } else {
            return ResponseEntity.ok(ApiResponse.success("Produit non présent dans les favoris", null));
        }
    }

    /**
     * Vérifier si un produit est en favori
     */
    @GetMapping("/check")
    @Operation(summary = "Vérifier", description = "Vérifier si un produit est en favori")
    public ResponseEntity<ApiResponse<Boolean>> isFavorite(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        boolean isFavorite = favoriteService.isFavorite(userId, productId);
        return ResponseEntity.ok(ApiResponse.success(isFavorite));
    }

    /**
     * Récupérer les favoris d'un utilisateur
     */
    @GetMapping
    @Operation(summary = "Mes favoris", description = "Récupérer les favoris de l'utilisateur")
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getUserFavorites(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<ProductResponse> response = favoriteService.getUserFavorites(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les favoris (liste)
     */
    @GetMapping("/list")
    @Operation(summary = "Liste des favoris", description = "Récupérer la liste des favoris")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getUserFavoritesList(@RequestParam Long userId) {
        List<ProductResponse> response = favoriteService.getUserFavoritesList(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
