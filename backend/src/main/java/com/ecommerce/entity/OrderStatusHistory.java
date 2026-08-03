package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entité Historique de statut de commande
 * Garde une trace de tous les changements de statut d'une commande
 */
@Entity
@Table(name = "order_status_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EOrderStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EOrderStatus newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    private String notes;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    @PrePersist
    public void prePersist() {
        this.changedAt = LocalDateTime.now();
    }
}
