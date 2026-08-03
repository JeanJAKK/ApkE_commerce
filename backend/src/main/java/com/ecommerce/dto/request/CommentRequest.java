package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la création d'un commentaire
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotBlank(message = "Le contenu du commentaire est requis")
    @Size(min = 2, max = 1000, message = "Le contenu doit contenir entre 2 et 1000 caractères")
    private String content;

    private Long parentId;
    
    private String attachment;
}
