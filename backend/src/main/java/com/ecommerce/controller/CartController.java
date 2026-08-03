package com.ecommerce.controller;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller pour le panier
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Panier", description = "API de gestion du panier")
public class CartController {

    private final CartService cartService;

    /**
     * Récupérer le panier
     */
    @GetMapping
    @Operation(summary = "Récupérer le panier", description = "Récupérer le panier de l'utilisateur")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@RequestParam Long userId) {
        CartResponse response = cartService.getCart(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Ajouter un article au panier
     */
    @PostMapping
    @Operation(summary = "Ajouter au panier", description = "Ajouter un article au panier")
    public ResponseEntity<ApiResponse<CartResponse>> addItem(
            @RequestParam Long userId,
            @Valid @RequestBody CartItemRequest request) {
        CartResponse response = cartService.addItem(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Article ajouté au panier", response));
    }

    /**
     * Mettre à jour la quantité d'un article
     */
    @PatchMapping("/items/{itemId}")
    @Operation(summary = "Mettre à jour la quantité", description = "Modifier la quantité d'un article")
    public ResponseEntity<ApiResponse<CartResponse>> updateItem(
            @PathVariable Long itemId,
            @RequestParam Long userId,
            @RequestParam int quantity) {
        CartResponse response = cartService.updateItem(userId, itemId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Quantité mise à jour", response));
    }

    /**
     * Supprimer un article du panier
     */
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Supprimer un article", description = "Supprimer un article du panier")
    public ResponseEntity<ApiResponse<CartResponse>> removeItem(
            @PathVariable Long itemId,
            @RequestParam Long userId) {
        CartResponse response = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(ApiResponse.success("Article supprimé du panier", response));
    }

    /**
     * Vider le panier
     */
    @DeleteMapping
    @Operation(summary = "Vider le panier", description = "Vider complètement le panier")
    public ResponseEntity<ApiResponse<Void>> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Panier vidé", null));
    }

    /**
     * Appliquer un code promo
     */
    @PostMapping("/promo")
    @Operation(summary = "Appliquer un code promo", description = "Appliquer un code promo au panier")
    public ResponseEntity<ApiResponse<CartResponse>> applyPromoCode(
            @RequestParam Long userId,
            @RequestParam String code) {
        CartResponse response = cartService.applyPromoCode(userId, code);
        return ResponseEntity.ok(ApiResponse.success("Code promo appliqué", response));
    }
}
