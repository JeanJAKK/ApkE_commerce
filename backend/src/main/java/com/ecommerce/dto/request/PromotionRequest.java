package com.ecommerce.dto.request;

import com.ecommerce.entity.EPromotionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour la création/modification d'une promotion
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {

    @NotBlank(message = "Le code promo est requis")
    @Size(min = 3, max = 50, message = "Le code doit contenir entre 3 et 50 caractères")
    private String code;

    @NotBlank(message = "Le titre est requis")
    private String title;

    private String description;

    @NotNull(message = "Le type de promotion est requis")
    private EPromotionType type;

    @DecimalMin(value = "0.00", message = "La valeur de réduction doit être positive")
    private BigDecimal discountValue;

    @Min(value = 1, message = "Le pourcentage doit être entre 1 et 100")
    @Max(value = 100, message = "Le pourcentage doit être entre 1 et 100")
    private Integer discountPercent;

    @DecimalMin(value = "0.00", message = "Le montant minimum doit être positif")
    private BigDecimal minimumOrderAmount;

    @DecimalMin(value = "0.00", message = "La réduction maximale doit être positive")
    private BigDecimal maximumDiscountAmount;

    private int usageLimit = 0;
    private int perUserLimit = 1;

    @NotNull(message = "La date de début est requise")
    private LocalDateTime startDate;

    @NotNull(message = "La date de fin est requise")
    private LocalDateTime endDate;

    private Long categoryId;
    private Long productId;
}
