package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la création/modification d'une catégorie
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Le nom de la catégorie est requis")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    private String description;
    private String image;
    private String icon;
    private int position = 0;
    private boolean active = true;
    private Long parentId;
}
