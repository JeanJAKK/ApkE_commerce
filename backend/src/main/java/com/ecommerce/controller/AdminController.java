package com.ecommerce.controller;

import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.DashboardResponse;
import com.ecommerce.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour le tableau de bord administrateur
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Administration", description = "API d'administration")
public class AdminController {

    private final DashboardService dashboardService;

    /**
     * Obtenir les statistiques du tableau de bord
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Tableau de bord", description = "Obtenir les statistiques du tableau de bord")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        DashboardResponse response = dashboardService.getDashboard();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
