package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la réponse des paramètres du site
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSettingsResponse {
    private Long id;
    private String siteName;
    private String siteSlogan;
    private String logo;
    private String favicon;
    private String description;
    private String primaryColor;
    private String secondaryColor;
    private String accentColor;
    private boolean darkModeEnabled;
    private String primaryFont;
    private String secondaryFont;
    private String heroBanner;
    private String promoBanner1;
    private String promoBanner2;
    private String promoBanner3;
    private String contactEmail;
    private String contactPhone;
    private String contactAddress;
    private String contactHours;
    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    private String youtubeUrl;
    private String whatsappNumber;
    private String telegramUrl;
    private String privacyPolicy;
    private String termsConditions;
    private String refundPolicy;
    private boolean maintenanceMode;
    private String maintenanceMessage;
    private boolean freeShippingEnabled;
    private double freeShippingThreshold;
    private double defaultShippingCost;
    private double taxRate;
    private boolean taxIncluded;
    private String currencyCode;
    private String currencySymbol;
    private String welcomeMessage;
}
