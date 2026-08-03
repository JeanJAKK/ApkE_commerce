package com.ecommerce.service;

import com.ecommerce.dto.response.NotificationResponse;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.NotificationRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service pour la gestion des notifications
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    /**
     * Créer une notification
     */
    @Transactional
    public void create(Long userId, String title, String message, ENotificationType type, String link, Long relatedId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Notification notification = Notification.builder()
            .user(user)
            .title(title)
            .message(message)
            .type(type)
            .link(link)
            .relatedId(relatedId)
            .read(false)
            .build();

        notificationRepository.save(notification);
        log.debug("Notification créée pour {}: {}", user.getEmail(), title);
    }

    /**
     * Créer une notification pour tous les administrateurs
     */
    @Transactional
    public void createForAdmins(String title, String message, ENotificationType type, String link, Long relatedId) {
        List<User> admins = userRepository.findAll().stream()
            .filter(User::isAdmin)
            .toList();

        for (User admin : admins) {
            create(admin.getId(), title, message, type, link, relatedId);
        }
    }

    /**
     * Créer notification de nouvelle commande
     */
    public void createOrderNotification(Order order) {
        createForAdmins(
            "Nouvelle commande",
            "Commande " + order.getOrderNumber() + " de " + order.getCustomerName(),
            ENotificationType.NEW_ORDER,
            "/admin/orders/" + order.getId(),
            order.getId()
        );
    }

    /**
     * Créer notification de mise à jour de statut
     */
    public void createStatusUpdateNotification(Order order, EOrderStatus oldStatus, EOrderStatus newStatus) {
        String message = String.format("Commande %s: %s → %s",
            order.getOrderNumber(), oldStatus.getDisplayName(), newStatus.getDisplayName());

        if (order.getUser() != null) {
            create(
                order.getUser().getId(),
                "Mise à jour de votre commande",
                message,
                ENotificationType.ORDER_CONFIRMED,
                "/orders/" + order.getOrderNumber(),
                order.getId()
            );
        }

        // Notification pour les admins si annulation
        if (newStatus == EOrderStatus.CANCELLED) {
            createForAdmins(
                "Commande annulée",
                message,
                ENotificationType.ORDER_CANCELLED,
                "/admin/orders/" + order.getId(),
                order.getId()
            );
        }
    }

    /**
     * Créer notification de nouvel avis
     */
    public void createReviewNotification(Review review) {
        createForAdmins(
            "Nouvel avis",
            review.getUser().getFullName() + " a laissé un avis de " + review.getRating() + "/5 sur " + review.getProduct().getName(),
            ENotificationType.NEW_REVIEW,
            "/admin/reviews/" + review.getId(),
            review.getId()
        );
    }

    /**
     * Créer notification de nouveau commentaire
     */
    public void createCommentNotification(Comment comment) {
        createForAdmins(
            "Nouveau commentaire",
            comment.getUser().getFullName() + " a commenté sur " + comment.getProduct().getName(),
            ENotificationType.NEW_COMMENT,
            "/admin/comments/" + comment.getId(),
            comment.getId()
        );
    }

    /**
     * Créer notification de stock faible
     */
    public void createLowStockNotification(Product product) {
        createForAdmins(
            "Stock faible",
            "Le produit " + product.getName() + " a seulement " + product.getStock() + " unités en stock",
            ENotificationType.LOW_STOCK,
            "/admin/products/" + product.getId(),
            product.getId()
        );
    }

    /**
     * Récupérer les notifications d'un utilisateur
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> getByUser(Long userId) {
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId).stream()
            .map(this::toResponse)
            .toList();
    }

    /**
     * Récupérer les notifications paginées
     */
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getByUserPaginated(Long userId, int page, int size) {
        return notificationRepository.findByUserId(userId, PageRequest.of(page, size))
            .map(this::toResponse);
    }

    /**
     * Marquer une notification comme lue
     */
    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * Marquer toutes les notifications comme lues
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId);
    }

    /**
     * Supprimer une notification
     */
    @Transactional
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    /**
     * Compter les notifications non lues
     */
    @Transactional(readOnly = true)
    public long countUnread(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    /**
     * Récupérer les notifications récentes pour le dashboard
     */
    @Transactional(readOnly = true)
    public List<NotificationResponse> getRecentForDashboard(int limit) {
        return notificationRepository.findRecentNotifications(PageRequest.of(0, limit))
            .map(this::toResponse)
            .toList();
    }

    private NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
            .id(notification.getId())
            .title(notification.getTitle())
            .message(notification.getMessage())
            .type(notification.getType())
            .typeDisplayName(notification.getType().getDisplayName())
            .read(notification.isRead())
            .link(notification.getLink())
            .relatedId(notification.getRelatedId())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
