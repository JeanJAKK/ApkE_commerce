package com.ecommerce.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO pour la recherche avancée de produits
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String query;
    private Long categoryId;
    private Long subcategoryId;
    
    @DecimalMin(value = "0.00", message = "Le prix minimum doit être positif")
    private BigDecimal minPrice;
    
    @DecimalMin(value = "0.00", message = "Le prix maximum doit être positif")
    private BigDecimal maxPrice;
    
    private String brand;
    private Set<String> colors;
    private Set<String> sizes;
    private Boolean inStock;
    private Boolean featured;
    private Boolean onSale;
    private Boolean newArrival;
    private String sortBy;
    private String sortOrder;
    private int page = 0;
    private int size = 20;
}
