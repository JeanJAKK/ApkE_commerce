package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entité Favori
 * Représente la relation entre un utilisateur et ses produits favoris
 */
@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDateTime.now();
    }
}
