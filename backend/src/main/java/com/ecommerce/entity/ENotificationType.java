package com.ecommerce.entity;

/**
 * Types de notification disponibles
 */
public enum ENotificationType {
    NEW_ORDER("Nouvelle commande"),
    ORDER_CANCELLED("Commande annulée"),
    ORDER_CONFIRMED("Commande confirmée"),
    ORDER_SHIPPED("Commande expédiée"),
    ORDER_DELIVERED("Commande livrée"),
    NEW_REVIEW("Nouvel avis"),
    NEW_COMMENT("Nouveau commentaire"),
    LOW_STOCK("Stock faible"),
    OUT_OF_STOCK("Rupture de stock"),
    NEW_USER("Nouvel utilisateur"),
    PAYMENT_RECEIVED("Paiement reçu"),
    SYSTEM("Système");

    private final String displayName;

    ENotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
