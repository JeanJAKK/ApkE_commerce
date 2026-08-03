package com.ecommerce.mapper;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.entity.Review;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Review <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "verified", constant = "false")
    @Mapping(target = "featured", constant = "false")
    @Mapping(target = "visible", constant = "true")
    @Mapping(target = "helpfulVotes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review toEntity(ReviewRequest request);

    @Mapping(target = "userName", expression = "java(review.getUser().getFullName())")
    @Mapping(target = "userAvatar", expression = "java(review.getUser().getAvatar())")
    @Mapping(target = "helpfulCount", expression = "java(review.getHelpfulCount())")
    ReviewResponse toResponse(Review review);

    List<ReviewResponse> toResponseList(List<Review> reviews);
}
