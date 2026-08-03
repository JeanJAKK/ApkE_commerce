package com.ecommerce.mapper;

import com.ecommerce.dto.request.SiteSettingsRequest;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.entity.SiteSettings;
import org.mapstruct.*;

/**
 * Mapper pour les conversions SiteSettings <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteSettingsMapper {

    SiteSettings toEntity(SiteSettingsRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(SiteSettingsRequest request, @MappingTarget SiteSettings settings);

    SiteSettingsResponse toResponse(SiteSettings settings);
}
