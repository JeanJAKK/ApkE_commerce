package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO pour la réponse produit
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String description;
    private String specifications;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private BigDecimal discountedPrice;
    private Integer discountPercent;
    private int stock;
    private boolean inStock;
    private String sku;
    private String brand;
    private Set<String> colors;
    private Set<String> sizes;
    private List<String> images;
    private String mainImage;
    private boolean featured;
    private boolean newArrival;
    private boolean onSale;
    private boolean active;
    private int viewCount;
    private int soldCount;
    private Long categoryId;
    private String categoryName;
    private Long subcategoryId;
    private String subcategoryName;
    private double averageRating;
    private int reviewCount;
    private boolean isFavorite;
    private int favoriteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
