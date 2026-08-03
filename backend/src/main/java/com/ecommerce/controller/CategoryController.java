package com.ecommerce.controller;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour les catégories
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories", description = "API de gestion des catégories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Créer une nouvelle catégorie (Admin)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer une catégorie", description = "Créer une nouvelle catégorie")
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Catégorie créée avec succès", response));
    }

    /**
     * Mettre à jour une catégorie (Admin)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour une catégorie", description = "Mettre à jour une catégorie existante")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Catégorie mise à jour avec succès", response));
    }

    /**
     * Supprimer une catégorie (Admin)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une catégorie", description = "Supprimer une catégorie")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Catégorie supprimée avec succès", null));
    }

    /**
     * Récupérer une catégorie par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une catégorie", description = "Récupérer une catégorie par son ID")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer une catégorie par slug
     */
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Récupérer par slug", description = "Récupérer une catégorie par son slug")
    public ResponseEntity<ApiResponse<CategoryResponse>> getBySlug(@PathVariable String slug) {
        CategoryResponse response = categoryService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les catégories principales
     */
    @GetMapping("/main")
    @Operation(summary = "Catégories principales", description = "Récupérer les catégories principales")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getMainCategories() {
        List<CategoryResponse> response = categoryService.getMainCategories();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les sous-catégories d'une catégorie
     */
    @GetMapping("/{parentId}/subcategories")
    @Operation(summary = "Sous-catégories", description = "Récupérer les sous-catégories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getSubcategories(@PathVariable Long parentId) {
        List<CategoryResponse> response = categoryService.getSubcategories(parentId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer toutes les catégories actives
     */
    @GetMapping
    @Operation(summary = "Toutes les catégories", description = "Récupérer toutes les catégories actives")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        List<CategoryResponse> response = categoryService.getAllWithSubcategories();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Réorganiser les catégories (Admin)
     */
    @PutMapping("/reorder")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Réorganiser", description = "Réorganiser l'ordre des catégories")
    public ResponseEntity<ApiResponse<Void>> reorder(@RequestBody List<Long> categoryIds) {
        categoryService.reorder(categoryIds);
        return ResponseEntity.ok(ApiResponse.success("Catégories réorganisées avec succès", null));
    }

    /**
     * Activer/Désactiver une catégorie (Admin)
     */
    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activer/Désactiver", description = "Activer ou désactiver une catégorie")
    public ResponseEntity<ApiResponse<Void>> toggleActive(@PathVariable Long id) {
        categoryService.toggleActive(id);
        return ResponseEntity.ok(ApiResponse.success("Statut de la catégorie modifié", null));
    }
}
