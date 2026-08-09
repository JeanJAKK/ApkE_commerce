package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité Utilisateur
 * Représente un utilisateur du système (client ou administrateur)
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phone;

    private String address;
    
    private String city;
    
    private String country;

    private String avatar;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean blocked = false;

    private String resetToken;

    private String refreshToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    /**
     * Vérifie si l'utilisateur est administrateur
     */
    public boolean isAdmin() {
        return roles.stream()
            .anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);
    }

    /**
     * Obtient le nom complet de l'utilisateur
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
