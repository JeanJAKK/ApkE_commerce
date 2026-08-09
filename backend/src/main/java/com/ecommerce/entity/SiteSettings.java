package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entité Paramètres du site
 * Stocke tous les paramètres de personnalisation du site
 */
@Entity
@Table(name = "site_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SiteSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identité du site
    private String siteName;
    private String siteSlogan;
    private String logo;
    private String favicon;
    private String description;

    // Couleurs
    private String primaryColor;
    private String secondaryColor;
    private String accentColor;

    // Thème
    private boolean darkModeEnabled = false;

    // Typographie
    private String primaryFont;
    private String secondaryFont;

    // Images de la page d'accueil
    private String heroBanner;
    private String promoBanner1;
    private String promoBanner2;
    private String promoBanner3;

    // Contact
    private String contactEmail;
    private String contactPhone;
    private String contactAddress;
    private String contactHours;

    // Réseaux sociaux
    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    private String youtubeUrl;
    private String whatsappNumber;
    private String telegramUrl;

    // Mentions légales
    @Column(columnDefinition = "TEXT")
    private String privacyPolicy;

    @Column(columnDefinition = "TEXT")
    private String termsConditions;

    @Column(columnDefinition = "TEXT")
    private String refundPolicy;

    // Configuration
    private boolean maintenanceMode = false;
    private String maintenanceMessage;

    // Livraison
    private boolean freeShippingEnabled = true;
    private double freeShippingThreshold = 100.0;
    private double defaultShippingCost = 10.0;

    // Taxes
    private double taxRate = 0.0;
    private boolean taxIncluded = true;

    // Monnaie
    private String currencyCode = "XOF";
    private String currencySymbol = "CFA";

    @Column(columnDefinition = "TEXT")
    private String welcomeMessage;
}
