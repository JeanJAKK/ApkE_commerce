package com.ecommerce.mapper;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.response.OrderItemResponse;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.entity.Order;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Order <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "paymentStatus", constant = "PENDING")
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "shippingCost", ignore = true)
    @Mapping(target = "tax", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRequest request);

    @Mapping(target = "statusDisplayName", expression = "java(order.getStatus().getDisplayName())")
    @Mapping(target = "paymentMethodDisplayName", expression = "java(order.getPaymentMethod() != null ? order.getPaymentMethod().getDisplayName() : null)")
    @Mapping(target = "itemCount", expression = "java(order.getItems() != null ? order.getItems().size() : 0)")
    @Mapping(target = "items", source = "items", qualifiedByName = "mapOrderItems")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    @Named("mapOrderItems")
    default List<OrderItemResponse> mapOrderItems(List<com.ecommerce.entity.OrderItem> items) {
        if (items == null) return null;
        return items.stream()
            .map(this::mapOrderItem)
            .toList();
    }

    OrderItemResponse mapOrderItem(com.ecommerce.entity.OrderItem item);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "productName", expression = "java(item.getProduct() != null ? item.getProduct().getName() : item.getProductName())")
    @Mapping(target = "productImage", expression = "java(getProductImage(item))")
    OrderItemResponse toItemResponse(com.ecommerce.entity.OrderItem item);

    default String getProductImage(com.ecommerce.entity.OrderItem item) {
        if (item.getProduct() != null && item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
            return item.getProduct().getImages().get(0);
        }
        return item.getProductImage();
    }
}
