package com.ecommerce.mapper;

import com.ecommerce.dto.request.PromotionRequest;
import com.ecommerce.dto.response.PromotionResponse;
import com.ecommerce.entity.Promotion;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Promotion <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromotionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "usedCount", constant = "0")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Promotion toEntity(PromotionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "usedCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(PromotionRequest request, @MappingTarget Promotion promotion);

    @Mapping(target = "typeDisplayName", expression = "java(promotion.getType().getDisplayName())")
    @Mapping(target = "valid", expression = "java(promotion.isValid())")
    PromotionResponse toResponse(Promotion promotion);

    List<PromotionResponse> toResponseList(List<Promotion> promotions);
}
