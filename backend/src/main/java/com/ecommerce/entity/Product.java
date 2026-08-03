package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entité Produit
 * Représente un produit dans le catalogue
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal oldPrice;

    private Integer discountPercent;

    @Column(nullable = false)
    private int stock = 0;

    private String sku;

    private String brand;

    @ElementCollection
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    @Builder.Default
    private Set<String> colors = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    @Builder.Default
    private Set<String> sizes = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String specifications;

    private boolean featured = false;
    
    private boolean newArrival = false;
    
    private boolean onSale = false;

    @Column(nullable = false)
    private boolean active = true;

    private boolean archived = false;

    private int viewCount = 0;
    
    private int soldCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id")
    private Category subcategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "favorites",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<User> favoritedBy = new HashSet<>();

    /**
     * Calcule le prix après réduction
     */
    public BigDecimal getDiscountedPrice() {
        if (discountPercent != null && discountPercent > 0 && oldPrice != null) {
            return oldPrice.subtract(
                oldPrice.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100))
            );
        }
        return price;
    }

    /**
     * Vérifie si le produit est en rupture de stock
     */
    public boolean isOutOfStock() {
        return stock <= 0;
    }

    /**
     * Obtient la note moyenne du produit
     */
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0);
    }

    /**
     * Obtient le nombre d'avis
     */
    public int getReviewCount() {
        return reviews.size();
    }
}
