package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Entité Article du panier
 * Représente un produit dans le panier d'un utilisateur
 */
@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

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
    private int quantity;

    private String selectedColor;

    private String selectedSize;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        this.addedAt = LocalDateTime.now();
    }

    /**
     * Calcule le prix total de l'article
     */
    public BigDecimal getTotalPrice() {
        if (product != null && product.getDiscountedPrice() != null) {
            return product.getDiscountedPrice().multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }
}
