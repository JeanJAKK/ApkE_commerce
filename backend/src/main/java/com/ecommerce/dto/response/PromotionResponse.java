package com.ecommerce.dto.response;

import com.ecommerce.entity.EPromotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour la réponse de promotion
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private EPromotionType type;
    private String typeDisplayName;
    private BigDecimal discountValue;
    private Integer discountPercent;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
    private int usageLimit;
    private int usedCount;
    private int perUserLimit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private boolean valid;
    private Long categoryId;
    private Long productId;
}
