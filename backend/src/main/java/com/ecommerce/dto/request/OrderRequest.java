package com.ecommerce.dto.request;

import com.ecommerce.entity.EPaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO pour la création d'une commande
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Le nom du client est requis")
    private String customerName;

    @NotBlank(message = "L'email du client est requis")
    @Email(message = "Format d'email invalide")
    private String customerEmail;

    @NotBlank(message = "Le téléphone du client est requis")
    private String customerPhone;

    @NotBlank(message = "L'adresse de livraison est requise")
    private String shippingAddress;

    private String shippingCity;
    private String shippingCountry;

    @NotNull(message = "La méthode de paiement est requise")
    private EPaymentMethod paymentMethod;

    private List<OrderItemRequest> items;
    
    private String promoCode;
    
    private String notes;
}
