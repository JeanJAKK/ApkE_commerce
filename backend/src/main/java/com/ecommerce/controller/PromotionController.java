package com.ecommerce.controller;

import com.ecommerce.dto.request.PromotionRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PromotionResponse;
import com.ecommerce.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour les promotions
 */
@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@Tag(name = "Promotions", description = "API de gestion des promotions")
public class PromotionController {

    private final PromotionService promotionService;

    /**
     * Créer une promotion (Admin)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer une promotion", description = "Créer une nouvelle promotion")
    public ResponseEntity<ApiResponse<PromotionResponse>> create(@Valid @RequestBody PromotionRequest request) {
        PromotionResponse response = promotionService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Promotion créée avec succès", response));
    }

    /**
     * Mettre à jour une promotion (Admin)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour une promotion", description = "Mettre à jour une promotion existante")
    public ResponseEntity<ApiResponse<PromotionResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PromotionRequest request) {
        PromotionResponse response = promotionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Promotion mise à jour avec succès", response));
    }

    /**
     * Supprimer une promotion (Admin)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une promotion", description = "Supprimer une promotion")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion supprimée avec succès", null));
    }

    /**
     * Récupérer une promotion par ID (Admin)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Récupérer une promotion", description = "Récupérer une promotion par son ID")
    public ResponseEntity<ApiResponse<PromotionResponse>> getById(@PathVariable Long id) {
        PromotionResponse response = promotionService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer une promotion par code
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "Récupérer par code", description = "Récupérer une promotion par son code")
    public ResponseEntity<ApiResponse<PromotionResponse>> getByCode(@PathVariable String code) {
        PromotionResponse response = promotionService.getValidPromotion(code);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer toutes les promotions (Admin)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Toutes les promotions", description = "Récupérer toutes les promotions")
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> getAll() {
        List<PromotionResponse> response = promotionService.getAll();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les promotions actives
     */
    @GetMapping("/active")
    @Operation(summary = "Promotions actives", description = "Récupérer les promotions actives")
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> getActive() {
        List<PromotionResponse> response = promotionService.getActive();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Activer/Désactiver une promotion (Admin)
     */
    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activer/Désactiver", description = "Activer ou désactiver une promotion")
    public ResponseEntity<ApiResponse<PromotionResponse>> toggleActive(@PathVariable Long id) {
        PromotionResponse response = promotionService.toggleActive(id);
        return ResponseEntity.ok(ApiResponse.success("Statut de la promotion modifié", response));
    }
}
