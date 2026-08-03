package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour la réponse d'article du panier
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal unitPrice;
    private BigDecimal discountedPrice;
    private int quantity;
    private BigDecimal totalPrice;
    private String selectedColor;
    private String selectedSize;
    private int availableStock;
    private boolean inStock;
    private LocalDateTime addedAt;
}
