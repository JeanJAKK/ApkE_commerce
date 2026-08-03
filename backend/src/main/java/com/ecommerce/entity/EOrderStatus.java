package com.ecommerce.entity;

/**
 * Statuts de commande disponibles
 */
public enum EOrderStatus {
    PENDING("En attente"),
    CONFIRMED("Confirmée"),
    PREPARING("En préparation"),
    SHIPPED("Expédiée"),
    DELIVERED("Livrée"),
    CANCELLED("Annulée"),
    REFUNDED("Remboursée");

    private final String displayName;

    EOrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
