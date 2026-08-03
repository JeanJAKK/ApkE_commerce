package com.ecommerce.entity;

/**
 * Statuts de paiement disponibles
 */
public enum EPaymentStatus {
    PENDING("En attente"),
    PAID("Payé"),
    FAILED("Échoué"),
    REFUNDED("Remboursé"),
    PARTIALLY_REFUNDED("Partiellement remboursé");

    private final String displayName;

    EPaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
