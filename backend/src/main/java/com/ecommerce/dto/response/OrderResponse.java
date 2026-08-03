package com.ecommerce.dto.response;

import com.ecommerce.entity.EOrderStatus;
import com.ecommerce.entity.EPaymentMethod;
import com.ecommerce.entity.EPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO pour la réponse commande
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private EOrderStatus status;
    private String statusDisplayName;
    private EPaymentMethod paymentMethod;
    private String paymentMethodDisplayName;
    private EPaymentStatus paymentStatus;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String promoCode;
    private String trackingNumber;
    private String notes;
    private List<OrderItemResponse> items;
    private int itemCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
