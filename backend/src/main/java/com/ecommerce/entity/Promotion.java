package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité Promotion/Coupon
 * Représente une promotion ou un coupon de réduction
 */
@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Promotion extends BaseEntity {

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPromotionType type;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountValue;

    private Integer discountPercent;

    @Column(precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal maximumDiscountAmount;

    private int usageLimit;

    private int usedCount = 0;

    private int perUserLimit = 1;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Vérifie si la promotion est en cours
     */
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return active && now.isAfter(startDate) && now.isBefore(endDate) 
            && (usageLimit == 0 || usedCount < usageLimit);
    }

    /**
     * Calcule la réduction pour un montant donné
     */
    public BigDecimal calculateDiscount(BigDecimal amount) {
        if (minimumOrderAmount != null && amount.compareTo(minimumOrderAmount) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount;
        if (type == EPromotionType.PERCENTAGE) {
            discount = amount.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100));
        } else {
            discount = discountValue;
        }

        if (maximumDiscountAmount != null && discount.compareTo(maximumDiscountAmount) > 0) {
            discount = maximumDiscountAmount;
        }

        return discount.min(amount);
    }
}
