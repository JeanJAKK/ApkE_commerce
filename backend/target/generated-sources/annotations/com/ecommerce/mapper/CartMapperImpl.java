package com.ecommerce.mapper;

import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.entity.CartItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:49+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartItemResponse toItemResponse(CartItem item) {
        if ( item == null ) {
            return null;
        }

        CartItemResponse.CartItemResponseBuilder cartItemResponse = CartItemResponse.builder();

        cartItemResponse.id( item.getId() );
        cartItemResponse.quantity( item.getQuantity() );
        cartItemResponse.selectedColor( item.getSelectedColor() );
        cartItemResponse.selectedSize( item.getSelectedSize() );
        cartItemResponse.addedAt( item.getAddedAt() );

        cartItemResponse.productName( item.getProduct().getName() );
        cartItemResponse.productImage( getProductImage(item) );
        cartItemResponse.unitPrice( item.getProduct().getPrice() );
        cartItemResponse.discountedPrice( item.getProduct().getDiscountedPrice() );
        cartItemResponse.totalPrice( item.getTotalPrice() );
        cartItemResponse.availableStock( item.getProduct().getStock() );
        cartItemResponse.inStock( !item.getProduct().isOutOfStock() );

        return cartItemResponse.build();
    }

    @Override
    public List<CartItemResponse> toItemResponseList(List<CartItem> items) {
        if ( items == null ) {
            return null;
        }

        List<CartItemResponse> list = new ArrayList<CartItemResponse>( items.size() );
        for ( CartItem cartItem : items ) {
            list.add( toItemResponse( cartItem ) );
        }

        return list;
    }
}
