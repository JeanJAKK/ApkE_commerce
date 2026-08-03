package com.ecommerce.entity;

/**
 * Types de mouvement de stock
 */
public enum EStockMovementType {
    PURCHASE("Achat/Réapprovisionnement"),
    SALE("Vente"),
    RETURN("Retour"),
    ADJUSTMENT("Ajustement"),
    DAMAGED("Endommagé/Perdu"),
    RESERVATION("Réservation"),
    RELEASE("Libération");

    private final String displayName;

    EStockMovementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
