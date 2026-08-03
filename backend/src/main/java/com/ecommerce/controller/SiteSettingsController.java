package com.ecommerce.controller;

import com.ecommerce.dto.request.SiteSettingsRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.service.SiteSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour les paramètres du site
 */
@RestController
@RequestMapping("/api/site-settings")
@RequiredArgsConstructor
@Tag(name = "Paramètres du site", description = "API de gestion des paramètres du site")
public class SiteSettingsController {

    private final SiteSettingsService siteSettingsService;

    /**
     * Récupérer les paramètres du site
     */
    @GetMapping
    @Operation(summary = "Récupérer les paramètres", description = "Récupérer les paramètres du site")
    public ResponseEntity<ApiResponse<SiteSettingsResponse>> getSettings() {
        SiteSettingsResponse response = siteSettingsService.getSettings();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Mettre à jour les paramètres du site (Admin)
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour les paramètres", description = "Mettre à jour les paramètres du site")
    public ResponseEntity<ApiResponse<SiteSettingsResponse>> updateSettings(
            @RequestBody SiteSettingsRequest request) {
        SiteSettingsResponse response = siteSettingsService.updateSettings(request);
        return ResponseEntity.ok(ApiResponse.success("Paramètres mis à jour avec succès", response));
    }

    /**
     * Activer/Désactiver le mode maintenance (Admin)
     */
    @PostMapping("/maintenance")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mode maintenance", description = "Activer ou désactiver le mode maintenance")
    public ResponseEntity<ApiResponse<Void>> toggleMaintenanceMode() {
        siteSettingsService.toggleMaintenanceMode();
        return ResponseEntity.ok(ApiResponse.success("Mode maintenance basculé", null));
    }
}
