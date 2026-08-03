package com.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la mise à jour d'un utilisateur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String avatar;
}
