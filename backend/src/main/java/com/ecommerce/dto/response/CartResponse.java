package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO pour la réponse du panier
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private List<CartItemResponse> items;
    private int totalItems;
    private int totalQuantity;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String appliedPromoCode;
    private boolean freeShipping;
}
