package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO pour la réponse catégorie
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private String icon;
    private int position;
    private boolean active;
    private Long parentId;
    private String parentName;
    private List<CategoryResponse> subcategories;
    private int productCount;
}
