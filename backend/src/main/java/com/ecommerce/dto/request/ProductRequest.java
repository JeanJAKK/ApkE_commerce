package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * DTO pour la création/modification d'un produit
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Le nom du produit est requis")
    @Size(min = 3, max = 200, message = "Le nom doit contenir entre 3 et 200 caractères")
    private String name;

    @Size(max = 500, message = "La description courte ne peut pas dépasser 500 caractères")
    private String shortDescription;

    @NotBlank(message = "La description est requise")
    private String description;

    private String specifications;

    @NotNull(message = "Le prix est requis")
    @DecimalMin(value = "0.00", message = "Le prix doit être positif")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "L'ancien prix doit être positif")
    private BigDecimal oldPrice;

    @Min(value = 0, message = "Le pourcentage de réduction doit être entre 0 et 100")
    @Max(value = 100, message = "Le pourcentage de réduction doit être entre 0 et 100")
    private Integer discountPercent;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock = 0;

    @NotBlank(message = "La référence/SKU est requise")
    private String sku;

    private String brand;

    private Set<String> colors;
    private Set<String> sizes;
    private List<String> images;

    private Long categoryId;
    private Long subcategoryId;

    private boolean featured = false;
    private boolean newArrival = false;
    private boolean onSale = false;
    private boolean active = true;
}
