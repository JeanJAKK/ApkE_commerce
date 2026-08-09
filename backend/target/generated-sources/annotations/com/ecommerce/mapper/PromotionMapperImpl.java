package com.ecommerce.mapper;

import com.ecommerce.dto.request.PromotionRequest;
import com.ecommerce.dto.response.PromotionResponse;
import com.ecommerce.entity.Promotion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:50+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class PromotionMapperImpl implements PromotionMapper {

    @Override
    public Promotion toEntity(PromotionRequest request) {
        if ( request == null ) {
            return null;
        }

        Promotion.PromotionBuilder<?, ?> promotion = Promotion.builder();

        promotion.code( request.getCode() );
        promotion.title( request.getTitle() );
        promotion.description( request.getDescription() );
        promotion.type( request.getType() );
        promotion.discountValue( request.getDiscountValue() );
        promotion.discountPercent( request.getDiscountPercent() );
        promotion.minimumOrderAmount( request.getMinimumOrderAmount() );
        promotion.maximumDiscountAmount( request.getMaximumDiscountAmount() );
        promotion.usageLimit( request.getUsageLimit() );
        promotion.perUserLimit( request.getPerUserLimit() );
        promotion.startDate( request.getStartDate() );
        promotion.endDate( request.getEndDate() );

        promotion.usedCount( 0 );

        return promotion.build();
    }

    @Override
    public void updateEntity(PromotionRequest request, Promotion promotion) {
        if ( request == null ) {
            return;
        }

        promotion.setCode( request.getCode() );
        promotion.setTitle( request.getTitle() );
        promotion.setDescription( request.getDescription() );
        promotion.setType( request.getType() );
        promotion.setDiscountValue( request.getDiscountValue() );
        promotion.setDiscountPercent( request.getDiscountPercent() );
        promotion.setMinimumOrderAmount( request.getMinimumOrderAmount() );
        promotion.setMaximumDiscountAmount( request.getMaximumDiscountAmount() );
        promotion.setUsageLimit( request.getUsageLimit() );
        promotion.setPerUserLimit( request.getPerUserLimit() );
        promotion.setStartDate( request.getStartDate() );
        promotion.setEndDate( request.getEndDate() );
    }

    @Override
    public PromotionResponse toResponse(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionResponse.PromotionResponseBuilder promotionResponse = PromotionResponse.builder();

        promotionResponse.id( promotion.getId() );
        promotionResponse.code( promotion.getCode() );
        promotionResponse.title( promotion.getTitle() );
        promotionResponse.description( promotion.getDescription() );
        promotionResponse.type( promotion.getType() );
        promotionResponse.discountValue( promotion.getDiscountValue() );
        promotionResponse.discountPercent( promotion.getDiscountPercent() );
        promotionResponse.minimumOrderAmount( promotion.getMinimumOrderAmount() );
        promotionResponse.maximumDiscountAmount( promotion.getMaximumDiscountAmount() );
        promotionResponse.usageLimit( promotion.getUsageLimit() );
        promotionResponse.usedCount( promotion.getUsedCount() );
        promotionResponse.perUserLimit( promotion.getPerUserLimit() );
        promotionResponse.startDate( promotion.getStartDate() );
        promotionResponse.endDate( promotion.getEndDate() );
        promotionResponse.active( promotion.isActive() );

        promotionResponse.typeDisplayName( promotion.getType().getDisplayName() );
        promotionResponse.valid( promotion.isValid() );

        return promotionResponse.build();
    }

    @Override
    public List<PromotionResponse> toResponseList(List<Promotion> promotions) {
        if ( promotions == null ) {
            return null;
        }

        List<PromotionResponse> list = new ArrayList<PromotionResponse>( promotions.size() );
        for ( Promotion promotion : promotions ) {
            list.add( toResponse( promotion ) );
        }

        return list;
    }
}
