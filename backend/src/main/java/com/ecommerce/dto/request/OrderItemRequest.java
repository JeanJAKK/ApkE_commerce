package com.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour un article dans la commande
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "L'ID du produit est requis")
    private Long productId;

    @NotNull(message = "La quantité est requise")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private int quantity;

    private String selectedColor;
    private String selectedSize;
}
