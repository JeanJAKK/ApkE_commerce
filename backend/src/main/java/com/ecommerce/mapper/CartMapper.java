package com.ecommerce.mapper;

import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.entity.CartItem;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Cart <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(target = "productName", expression = "java(item.getProduct().getName())")
    @Mapping(target = "productImage", expression = "java(getProductImage(item))")
    @Mapping(target = "unitPrice", expression = "java(item.getProduct().getPrice())")
    @Mapping(target = "discountedPrice", expression = "java(item.getProduct().getDiscountedPrice())")
    @Mapping(target = "totalPrice", expression = "java(item.getTotalPrice())")
    @Mapping(target = "availableStock", expression = "java(item.getProduct().getStock())")
    @Mapping(target = "inStock", expression = "java(!item.getProduct().isOutOfStock())")
    CartItemResponse toItemResponse(CartItem item);

    List<CartItemResponse> toItemResponseList(List<CartItem> items);

    default String getProductImage(CartItem item) {
        if (item.getProduct() != null && item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
            return item.getProduct().getImages().get(0);
        }
        return null;
    }
}
