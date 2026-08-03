package com.ecommerce.entity;

/**
 * Types de promotion disponibles
 */
public enum EPromotionType {
    PERCENTAGE("Pourcentage"),
    FIXED("Montant fixe"),
    FREE_SHIPPING("Livraison gratuite"),
    BUY_X_GET_Y("Acheter X obtenir Y"),
    CATEGORY("Par catégorie"),
    PRODUCT("Par produit");

    private final String displayName;

    EPromotionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
