package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la demande de rafraîchissement du token
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Le refresh token est requis")
    private String refreshToken;
}
