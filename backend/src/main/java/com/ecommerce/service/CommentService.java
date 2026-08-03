package com.ecommerce.service;

import com.ecommerce.dto.request.CommentRequest;
import com.ecommerce.dto.response.CommentResponse;
import com.ecommerce.entity.Comment;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.mapper.CommentMapper;
import com.ecommerce.repository.CommentRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des commentaires
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final CommentMapper commentMapper;

    /**
     * Créer un commentaire
     */
    @Transactional
    public CommentResponse create(Long productId, Long userId, CommentRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", productId));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Comment comment = commentMapper.toEntity(request);
        comment.setProduct(product);
        comment.setUser(user);

        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire parent", "id", request.getParentId()));

            if (!parent.getProduct().getId().equals(productId)) {
                throw new BadRequestException("Le commentaire parent n'appartient pas à ce produit");
            }

            comment.setParent(parent);
        }

        comment = commentRepository.save(comment);
        log.info("Commentaire créé pour {} par {}", product.getName(), user.getFullName());

        // Notification pour l'administrateur
        notificationService.createCommentNotification(comment);

        return commentMapper.toResponse(comment);
    }

    /**
     * Répondre à un commentaire
     */
    @Transactional
    public CommentResponse reply(Long commentId, Long userId, CommentRequest request) {
        Comment parent = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", commentId));

        request.setParentId(commentId);
        return create(parent.getProduct().getId(), userId, request);
    }

    /**
     * Mettre à jour un commentaire
     */
    @Transactional
    public CommentResponse update(Long id, Long userId, CommentRequest request) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Vous ne pouvez pas modifier ce commentaire");
        }

        comment.setContent(request.getContent());
        comment.setAttachment(request.getAttachment());

        comment = commentRepository.save(comment);
        log.info("Commentaire mis à jour: {}", comment.getId());

        return commentMapper.toResponse(comment);
    }

    /**
     * Supprimer un commentaire
     */
    @Transactional
    public void delete(Long id, Long userId, boolean isAdmin) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", id));

        if (!isAdmin && !comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Vous ne pouvez pas supprimer ce commentaire");
        }

        commentRepository.delete(comment);
        log.info("Commentaire supprimé: {}", id);
    }

    /**
     * Récupérer les commentaires d'un produit
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getByProduct(Long productId) {
        return commentMapper.toResponseList(
            commentRepository.findVisibleCommentsByProductId(productId)
        );
    }

    /**
     * Récupérer les commentaires paginés d'un produit
     */
    @Transactional(readOnly = true)
    public Page<CommentResponse> getByProductPaginated(Long productId, int page, int size) {
        return commentRepository.findByProductIdPaginated(productId, PageRequest.of(page, size))
            .map(commentMapper::toResponse);
    }

    /**
     * Masquer/afficher un commentaire
     */
    @Transactional
    public CommentResponse toggleVisibility(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", id));

        comment.setVisible(!comment.isVisible());
        comment = commentRepository.save(comment);

        log.info("Commentaire {} {}", comment.getId(), comment.isVisible() ? "affiché" : "masqué");

        return commentMapper.toResponse(comment);
    }

    /**
     * Épingler/désépingler un commentaire
     */
    @Transactional
    public CommentResponse togglePinned(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", id));

        comment.setPinned(!comment.isPinned());
        comment = commentRepository.save(comment);

        log.info("Commentaire {} {}", comment.getId(), comment.isPinned() ? "épinglé" : "désépinglé");

        return commentMapper.toResponse(comment);
    }

    /**
     * Signaler un commentaire
     */
    @Transactional
    public void report(Long id, Long userId, String reason) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", id));

        comment.setReported(true);
        comment.setReportReason(reason);
        commentRepository.save(comment);

        log.info("Commentaire {} signalé par l'utilisateur {}", id, userId);
    }

    /**
     * Like un commentaire
     */
    @Transactional
    public CommentResponse likeComment(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commentaire", "id", id));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        if (comment.getLikedBy().contains(user)) {
            comment.getLikedBy().remove(user);
            log.info("Like retiré du commentaire {}", id);
        } else {
            comment.getLikedBy().add(user);
            log.info("Commentaire {} aimé par {}", id, userId);
        }

        comment = commentRepository.save(comment);
        return commentMapper.toResponse(comment);
    }

    /**
     * Récupérer les commentaires signalés
     */
    @Transactional(readOnly = true)
    public Page<CommentResponse> getReportedComments(int page, int size) {
        return commentRepository.findReportedComments(PageRequest.of(page, size))
            .map(commentMapper::toResponse);
    }
}
