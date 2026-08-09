package com.ecommerce.mapper;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.response.OrderItemResponse;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.entity.EOrderStatus;
import com.ecommerce.entity.EPaymentStatus;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
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
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderRequest request) {
        if ( request == null ) {
            return null;
        }

        Order.OrderBuilder<?, ?> order = Order.builder();

        order.customerName( request.getCustomerName() );
        order.customerEmail( request.getCustomerEmail() );
        order.customerPhone( request.getCustomerPhone() );
        order.shippingAddress( request.getShippingAddress() );
        order.shippingCity( request.getShippingCity() );
        order.shippingCountry( request.getShippingCountry() );
        order.paymentMethod( request.getPaymentMethod() );
        order.promoCode( request.getPromoCode() );
        order.notes( request.getNotes() );

        order.status( EOrderStatus.PENDING );
        order.paymentStatus( EPaymentStatus.PENDING );

        return order.build();
    }

    @Override
    public OrderResponse toResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.items( mapOrderItems( order.getItems() ) );
        orderResponse.id( order.getId() );
        orderResponse.orderNumber( order.getOrderNumber() );
        orderResponse.customerName( order.getCustomerName() );
        orderResponse.customerEmail( order.getCustomerEmail() );
        orderResponse.customerPhone( order.getCustomerPhone() );
        orderResponse.shippingAddress( order.getShippingAddress() );
        orderResponse.shippingCity( order.getShippingCity() );
        orderResponse.shippingCountry( order.getShippingCountry() );
        orderResponse.status( order.getStatus() );
        orderResponse.paymentMethod( order.getPaymentMethod() );
        orderResponse.paymentStatus( order.getPaymentStatus() );
        orderResponse.subtotal( order.getSubtotal() );
        orderResponse.shippingCost( order.getShippingCost() );
        orderResponse.tax( order.getTax() );
        orderResponse.discount( order.getDiscount() );
        orderResponse.total( order.getTotal() );
        orderResponse.promoCode( order.getPromoCode() );
        orderResponse.trackingNumber( order.getTrackingNumber() );
        orderResponse.notes( order.getNotes() );
        orderResponse.createdAt( order.getCreatedAt() );
        orderResponse.updatedAt( order.getUpdatedAt() );

        orderResponse.statusDisplayName( order.getStatus().getDisplayName() );
        orderResponse.paymentMethodDisplayName( order.getPaymentMethod() != null ? order.getPaymentMethod().getDisplayName() : null );
        orderResponse.itemCount( order.getItems() != null ? order.getItems().size() : 0 );

        return orderResponse.build();
    }

    @Override
    public List<OrderResponse> toResponseList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( orders.size() );
        for ( Order order : orders ) {
            list.add( toResponse( order ) );
        }

        return list;
    }

    @Override
    public OrderItemResponse mapOrderItem(OrderItem item) {
        if ( item == null ) {
            return null;
        }

        OrderItemResponse.OrderItemResponseBuilder orderItemResponse = OrderItemResponse.builder();

        orderItemResponse.id( item.getId() );
        orderItemResponse.productName( item.getProductName() );
        orderItemResponse.productImage( item.getProductImage() );
        orderItemResponse.quantity( item.getQuantity() );
        orderItemResponse.unitPrice( item.getUnitPrice() );
        orderItemResponse.totalPrice( item.getTotalPrice() );
        orderItemResponse.selectedColor( item.getSelectedColor() );
        orderItemResponse.selectedSize( item.getSelectedSize() );

        return orderItemResponse.build();
    }

    @Override
    public OrderItemResponse toItemResponse(OrderItem item) {
        if ( item == null ) {
            return null;
        }

        OrderItemResponse.OrderItemResponseBuilder orderItemResponse = OrderItemResponse.builder();

        orderItemResponse.id( item.getId() );
        orderItemResponse.quantity( item.getQuantity() );
        orderItemResponse.unitPrice( item.getUnitPrice() );
        orderItemResponse.totalPrice( item.getTotalPrice() );
        orderItemResponse.selectedColor( item.getSelectedColor() );
        orderItemResponse.selectedSize( item.getSelectedSize() );

        orderItemResponse.productName( item.getProduct() != null ? item.getProduct().getName() : item.getProductName() );
        orderItemResponse.productImage( getProductImage(item) );

        return orderItemResponse.build();
    }
}
