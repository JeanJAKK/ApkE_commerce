package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO pour la réponse d'article de commande
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String selectedColor;
    private String selectedSize;
}
