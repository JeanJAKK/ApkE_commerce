package com.ecommerce.controller;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.request.SearchRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour les produits
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "API de gestion des produits")
public class ProductController {

    private final ProductService productService;

    /**
     * Créer un nouveau produit (Admin)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un produit", description = "Créer un nouveau produit")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Produit créé avec succès", response));
    }

    /**
     * Mettre à jour un produit (Admin)
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un produit", description = "Mettre à jour un produit existant")
    public ResponseEntity<ApiResponse<ProductResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Produit mis à jour avec succès", response));
    }

    /**
     * Supprimer un produit (Admin)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un produit", description = "Supprimer un produit")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Produit supprimé avec succès", null));
    }

    /**
     * Récupérer un produit par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit", description = "Récupérer un produit par son ID")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Long id) {
        ProductResponse response = productService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer un produit par slug
     */
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Récupérer par slug", description = "Récupérer un produit par son slug")
    public ResponseEntity<ApiResponse<ProductResponse>> getBySlug(@PathVariable String slug) {
        ProductResponse response = productService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer tous les produits paginés
     */
    @GetMapping
    @Operation(summary = "Liste des produits", description = "Récupérer tous les produits paginés")
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        PageResponse<ProductResponse> response = productService.getAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Rechercher des produits
     */
    @PostMapping("/search")
    @Operation(summary = "Rechercher des produits", description = "Recherche avancée de produits")
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> search(
            @RequestBody SearchRequest request) {
        PageResponse<ProductResponse> response = productService.search(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les produits vedettes
     */
    @GetMapping("/featured")
    @Operation(summary = "Produits vedettes", description = "Récupérer les produits vedettes")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getFeatured(
            @RequestParam(defaultValue = "8") int limit) {
        List<ProductResponse> response = productService.getFeatured(limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les nouveautés
     */
    @GetMapping("/new-arrivals")
    @Operation(summary = "Nouveautés", description = "Récupérer les nouveautés")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getNewArrivals(
            @RequestParam(defaultValue = "8") int limit) {
        List<ProductResponse> response = productService.getNewArrivals(limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les produits en promotion
     */
    @GetMapping("/on-sale")
    @Operation(summary = "Promotions", description = "Récupérer les produits en promotion")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getOnSale(
            @RequestParam(defaultValue = "8") int limit) {
        List<ProductResponse> response = productService.getOnSale(limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les produits similaires
     */
    @GetMapping("/{id}/similar")
    @Operation(summary = "Produits similaires", description = "Récupérer les produits similaires")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getSimilar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "4") int limit) {
        List<ProductResponse> response = productService.getSimilar(id, limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les produits d'une catégorie
     */
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Produits par catégorie", description = "Récupérer les produits d'une catégorie")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "20") int limit) {
        List<ProductResponse> response = productService.getByCategory(categoryId, limit);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Archiver un produit (Admin)
     */
    @PostMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Archiver un produit", description = "Archiver un produit")
    public ResponseEntity<ApiResponse<Void>> archive(@PathVariable Long id) {
        productService.archive(id);
        return ResponseEntity.ok(ApiResponse.success("Produit archivé avec succès", null));
    }

    /**
     * Dupliquer un produit (Admin)
     */
    @PostMapping("/{id}/duplicate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Dupliquer un produit", description = "Dupliquer un produit existant")
    public ResponseEntity<ApiResponse<ProductResponse>> duplicate(@PathVariable Long id) {
        ProductResponse response = productService.duplicate(id);
        return ResponseEntity.ok(ApiResponse.success("Produit dupliqué avec succès", response));
    }

    /**
     * Récupérer les produits en rupture de stock (Admin)
     */
    @GetMapping("/out-of-stock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Produits en rupture", description = "Récupérer les produits en rupture de stock")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getOutOfStock() {
        List<ProductResponse> response = productService.getOutOfStock();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les produits à faible stock (Admin)
     */
    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Stock faible", description = "Récupérer les produits à faible stock")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getLowStock(
            @RequestParam(defaultValue = "10") int threshold) {
        List<ProductResponse> response = productService.getLowStock(threshold);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Mettre à jour le stock (Admin)
     */
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour le stock", description = "Mettre à jour la quantité en stock")
    public ResponseEntity<ApiResponse<Void>> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        productService.updateStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success("Stock mis à jour avec succès", null));
    }
}
