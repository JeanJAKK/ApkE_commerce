package com.ecommerce.controller;

import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.service.SiteSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour les informations publiques du site
 */
@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "Public", description = "API d'informations publiques")
public class PublicController {

    private final SiteSettingsService siteSettingsService;

    /**
     * Récupérer les informations publiques du site
     */
    @GetMapping("/info")
    @Operation(summary = "Informations du site", description = "Récupérer les informations publiques du site")
    public ResponseEntity<ApiResponse<SiteSettingsResponse>> getSiteInfo() {
        SiteSettingsResponse response = siteSettingsService.getSettings();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
