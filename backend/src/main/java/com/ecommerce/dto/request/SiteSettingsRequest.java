package com.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la modification des paramètres du site
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSettingsRequest {
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
    private Boolean darkModeEnabled;

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
    private String privacyPolicy;
    private String termsConditions;
    private String refundPolicy;

    // Configuration
    private Boolean maintenanceMode;
    private String maintenanceMessage;

    // Livraison
    private Boolean freeShippingEnabled;
    private Double freeShippingThreshold;
    private Double defaultShippingCost;

    // Taxes
    private Double taxRate;
    private Boolean taxIncluded;

    // Monnaie
    private String currencyCode;
    private String currencySymbol;

    private String welcomeMessage;
}
