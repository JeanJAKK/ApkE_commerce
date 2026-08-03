package com.ecommerce.controller;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller pour les avis
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Avis", description = "API de gestion des avis")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Créer un avis
     */
    @PostMapping("/product/{productId}")
    @Operation(summary = "Créer un avis", description = "Créer un nouvel avis sur un produit")
    public ResponseEntity<ApiResponse<ReviewResponse>> create(
            @PathVariable Long productId,
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.create(productId, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Avis créé avec succès", response));
    }

    /**
     * Mettre à jour un avis
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un avis", description = "Mettre à jour un avis existant")
    public ResponseEntity<ApiResponse<ReviewResponse>> update(
            @PathVariable Long id,
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.update(id, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Avis mis à jour avec succès", response));
    }

    /**
     * Supprimer un avis
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un avis", description = "Supprimer un avis")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {
        reviewService.delete(id, userId, isAdmin);
        return ResponseEntity.ok(ApiResponse.success("Avis supprimé avec succès", null));
    }

    /**
     * Récupérer les avis d'un produit
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "Avis d'un produit", description = "Récupérer les avis d'un produit")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getByProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<ReviewResponse> response = reviewService.getByProduct(productId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer un avis par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un avis", description = "Récupérer un avis par son ID")
    public ResponseEntity<ApiResponse<ReviewResponse>> getById(@PathVariable Long id) {
        ReviewResponse response = reviewService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Masquer/Afficher un avis (Admin)
     */
    @PatchMapping("/{id}/visibility")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Visibilité d'un avis", description = "Masquer ou afficher un avis")
    public ResponseEntity<ApiResponse<ReviewResponse>> toggleVisibility(@PathVariable Long id) {
        ReviewResponse response = reviewService.toggleVisibility(id);
        return ResponseEntity.ok(ApiResponse.success("Visibilité modifiée", response));
    }

    /**
     * Épingler/Désépingler un avis (Admin)
     */
    @PatchMapping("/{id}/featured")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Épingler un avis", description = "Épingler ou désépingler un avis")
    public ResponseEntity<ApiResponse<ReviewResponse>> toggleFeatured(@PathVariable Long id) {
        ReviewResponse response = reviewService.toggleFeatured(id);
        return ResponseEntity.ok(ApiResponse.success("Avis épinglé/désépinglé", response));
    }

    /**
     * Voter "utile" pour un avis
     */
    @PostMapping("/{id}/helpful")
    @Operation(summary = "Voter utile", description = "Voter qu'un avis est utile")
    public ResponseEntity<ApiResponse<Void>> voteHelpful(
            @PathVariable Long id,
            @RequestParam Long userId) {
        reviewService.voteHelpful(id, userId);
        return ResponseEntity.ok(ApiResponse.success("Vote enregistré", null));
    }

    /**
     * Récupérer les avis mis en avant
     */
    @GetMapping("/featured")
    @Operation(summary = "Avis mis en avant", description = "Récupérer les avis mis en avant")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getFeatured() {
        List<ReviewResponse> response = reviewService.getFeatured();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer la distribution des notes
     */
    @GetMapping("/product/{productId}/distribution")
    @Operation(summary = "Distribution des notes", description = "Récupérer la distribution des notes d'un produit")
    public ResponseEntity<ApiResponse<Map<Integer, Long>>> getRatingDistribution(@PathVariable Long productId) {
        Map<Integer, Long> response = reviewService.getRatingDistribution(productId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
