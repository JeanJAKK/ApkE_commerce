package com.ecommerce.mapper;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.entity.Review;
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
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review toEntity(ReviewRequest request) {
        if ( request == null ) {
            return null;
        }

        Review.ReviewBuilder<?, ?> review = Review.builder();

        review.rating( request.getRating() );
        review.title( request.getTitle() );
        review.content( request.getContent() );
        review.image( request.getImage() );

        review.verified( false );
        review.featured( false );
        review.visible( true );

        return review.build();
    }

    @Override
    public ReviewResponse toResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.id( review.getId() );
        reviewResponse.rating( review.getRating() );
        reviewResponse.title( review.getTitle() );
        reviewResponse.content( review.getContent() );
        reviewResponse.image( review.getImage() );
        reviewResponse.verified( review.isVerified() );
        reviewResponse.featured( review.isFeatured() );
        reviewResponse.visible( review.isVisible() );
        reviewResponse.createdAt( review.getCreatedAt() );

        reviewResponse.userName( review.getUser().getFullName() );
        reviewResponse.userAvatar( review.getUser().getAvatar() );
        reviewResponse.helpfulCount( review.getHelpfulCount() );

        return reviewResponse.build();
    }

    @Override
    public List<ReviewResponse> toResponseList(List<Review> reviews) {
        if ( reviews == null ) {
            return null;
        }

        List<ReviewResponse> list = new ArrayList<ReviewResponse>( reviews.size() );
        for ( Review review : reviews ) {
            list.add( toResponse( review ) );
        }

        return list;
    }
}
