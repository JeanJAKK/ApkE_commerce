package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO pour la réponse utilisateur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String avatar;
    private boolean enabled;
    private boolean blocked;
    private Set<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
