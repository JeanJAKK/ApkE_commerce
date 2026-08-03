package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Catégorie
 * Représente une catégorie de produits
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String image;

    private String icon;

    private int position = 0;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Category> subcategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    /**
     * Vérifie si la catégorie est une sous-catégorie
     */
    public boolean isSubcategory() {
        return parent != null;
    }

    /**
     * Obtient le chemin complet de la catégorie
     */
    public String getFullPath() {
        if (parent != null) {
            return parent.getName() + " > " + name;
        }
        return name;
    }
}
