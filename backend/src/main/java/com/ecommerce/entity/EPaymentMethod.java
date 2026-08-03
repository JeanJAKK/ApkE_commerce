package com.ecommerce.entity;

/**
 * Méthodes de paiement disponibles
 */
public enum EPaymentMethod {
    CASH_ON_DELIVERY("Paiement à la livraison"),
    MOBILE_MONEY("Mobile Money"),
    CREDIT_CARD("Carte bancaire"),
    PAYPAL("PayPal"),
    BANK_TRANSFER("Virement bancaire");

    private final String displayName;

    EPaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
