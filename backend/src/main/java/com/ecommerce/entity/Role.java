package com.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

/**
 * Entité Rôle
 * Définit les rôles disponibles dans le système
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole name;

    private String description;
}
