package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la création d'un avis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotNull(message = "La note est requise")
    @Min(value = 1, message = "La note doit être au moins 1")
    @Max(value = 5, message = "La note ne peut pas dépasser 5")
    private int rating;

    @NotBlank(message = "Le titre de l'avis est requis")
    @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
    private String title;

    @NotBlank(message = "Le contenu de l'avis est requis")
    @Size(min = 10, max = 2000, message = "Le contenu doit contenir entre 10 et 2000 caractères")
    private String content;

    private String image;
}
