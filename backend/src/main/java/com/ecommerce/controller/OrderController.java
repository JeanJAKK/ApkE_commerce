package com.ecommerce.controller;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.entity.EOrderStatus;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller pour les commandes
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Commandes", description = "API de gestion des commandes")
public class OrderController {

    private final OrderService orderService;

    /**
     * Créer une nouvelle commande
     */
    @PostMapping
    @Operation(summary = "Créer une commande", description = "Créer une nouvelle commande")
    public ResponseEntity<ApiResponse<OrderResponse>> create(
            @Valid @RequestBody OrderRequest request,
            @RequestParam(required = false) Long userId) {
        OrderResponse response = orderService.create(request, userId);
        return ResponseEntity.ok(ApiResponse.success("Commande créée avec succès", response));
    }

    /**
     * Récupérer une commande par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une commande", description = "Récupérer une commande par son ID")
    public ResponseEntity<ApiResponse<OrderResponse>> getById(@PathVariable Long id) {
        OrderResponse response = orderService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer une commande par numéro
     */
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Récupérer par numéro", description = "Récupérer une commande par son numéro")
    public ResponseEntity<ApiResponse<OrderResponse>> getByOrderNumber(@PathVariable String orderNumber) {
        OrderResponse response = orderService.getByOrderNumber(orderNumber);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les commandes d'un utilisateur
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Commandes utilisateur", description = "Récupérer les commandes d'un utilisateur")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> getByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<OrderResponse> response = orderService.getByUserId(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Rechercher des commandes (Admin)
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Rechercher", description = "Rechercher des commandes")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<OrderResponse> response = orderService.search(query, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Filtrer les commandes (Admin)
     */
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Filtrer", description = "Filtrer les commandes")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> filter(
            @RequestParam(required = false) EOrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<OrderResponse> response = orderService.filter(status, startDate, endDate, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Mettre à jour le statut d'une commande (Admin)
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour le statut", description = "Mettre à jour le statut d'une commande")
    public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam EOrderStatus status,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) Long adminId) {
        OrderResponse response = orderService.updateStatus(id, status, notes, adminId);
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour avec succès", response));
    }

    /**
     * Ajouter un numéro de suivi (Admin)
     */
    @PatchMapping("/{id}/tracking")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ajouter un numéro de suivi", description = "Ajouter un numéro de suivi à la commande")
    public ResponseEntity<ApiResponse<OrderResponse>> addTrackingNumber(
            @PathVariable Long id,
            @RequestParam String trackingNumber) {
        OrderResponse response = orderService.addTrackingNumber(id, trackingNumber);
        return ResponseEntity.ok(ApiResponse.success("Numéro de suivi ajouté", response));
    }

    /**
     * Récupérer les commandes récentes (Admin)
     */
    @GetMapping("/recent")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Commandes récentes", description = "Récupérer les commandes récentes")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getRecent(
            @RequestParam(defaultValue = "5") int limit) {
        List<OrderResponse> response = orderService.getRecent(limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les commandes en attente (Admin)
     */
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Commandes en attente", description = "Récupérer les commandes en attente")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getPending(
            @RequestParam(defaultValue = "5") int limit) {
        List<OrderResponse> response = orderService.getPending(limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
