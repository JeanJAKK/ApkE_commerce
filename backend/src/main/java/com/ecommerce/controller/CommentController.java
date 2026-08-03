package com.ecommerce.controller;

import com.ecommerce.dto.request.CommentRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.CommentResponse;
import com.ecommerce.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour les commentaires
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Commentaires", description = "API de gestion des commentaires")
public class CommentController {

    private final CommentService commentService;

    /**
     * Créer un commentaire
     */
    @PostMapping("/product/{productId}")
    @Operation(summary = "Créer un commentaire", description = "Créer un nouveau commentaire sur un produit")
    public ResponseEntity<ApiResponse<CommentResponse>> create(
            @PathVariable Long productId,
            @RequestParam Long userId,
            @Valid @RequestBody CommentRequest request) {
        CommentResponse response = commentService.create(productId, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Commentaire créé avec succès", response));
    }

    /**
     * Répondre à un commentaire
     */
    @PostMapping("/{id}/reply")
    @Operation(summary = "Répondre à un commentaire", description = "Répondre à un commentaire existant")
    public ResponseEntity<ApiResponse<CommentResponse>> reply(
            @PathVariable Long id,
            @RequestParam Long userId,
            @Valid @RequestBody CommentRequest request) {
        CommentResponse response = commentService.reply(id, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Réponse ajoutée", response));
    }

    /**
     * Mettre à jour un commentaire
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un commentaire", description = "Mettre à jour un commentaire")
    public ResponseEntity<ApiResponse<CommentResponse>> update(
            @PathVariable Long id,
            @RequestParam Long userId,
            @Valid @RequestBody CommentRequest request) {
        CommentResponse response = commentService.update(id, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Commentaire mis à jour", response));
    }

    /**
     * Supprimer un commentaire
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un commentaire", description = "Supprimer un commentaire")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {
        commentService.delete(id, userId, isAdmin);
        return ResponseEntity.ok(ApiResponse.success("Commentaire supprimé", null));
    }

    /**
     * Récupérer les commentaires d'un produit
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "Commentaires d'un produit", description = "Récupérer les commentaires d'un produit")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getByProduct(@PathVariable Long productId) {
        List<CommentResponse> response = commentService.getByProduct(productId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Masquer/Afficher un commentaire (Admin)
     */
    @PatchMapping("/{id}/visibility")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Visibilité", description = "Masquer ou afficher un commentaire")
    public ResponseEntity<ApiResponse<CommentResponse>> toggleVisibility(@PathVariable Long id) {
        CommentResponse response = commentService.toggleVisibility(id);
        return ResponseEntity.ok(ApiResponse.success("Visibilité modifiée", response));
    }

    /**
     * Épingler/Désépingler un commentaire (Admin)
     */
    @PatchMapping("/{id}/pin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Épingler", description = "Épingler ou désépingler un commentaire")
    public ResponseEntity<ApiResponse<CommentResponse>> togglePinned(@PathVariable Long id) {
        CommentResponse response = commentService.togglePinned(id);
        return ResponseEntity.ok(ApiResponse.success("Commentaire épinglé/désépinglé", response));
    }

    /**
     * Signaler un commentaire
     */
    @PostMapping("/{id}/report")
    @Operation(summary = "Signaler", description = "Signaler un commentaire")
    public ResponseEntity<ApiResponse<Void>> report(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam String reason) {
        commentService.report(id, userId, reason);
        return ResponseEntity.ok(ApiResponse.success("Commentaire signalé", null));
    }

    /**
     * Like un commentaire
     */
    @PostMapping("/{id}/like")
    @Operation(summary = "Liker", description = "Liker ou disliker un commentaire")
    public ResponseEntity<ApiResponse<CommentResponse>> likeComment(
            @PathVariable Long id,
            @RequestParam Long userId) {
        CommentResponse response = commentService.likeComment(id, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Récupérer les commentaires signalés (Admin)
     */
    @GetMapping("/reported")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Commentaires signalés", description = "Récupérer les commentaires signalés")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> getReportedComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CommentResponse> response = commentService.getReportedComments(page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
