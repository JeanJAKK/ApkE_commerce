package com.ecommerce.repository;

import com.ecommerce.entity.ReviewHelpful;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository pour les opérations sur les votes utiles des avis
 */
@Repository
public interface ReviewHelpfulRepository extends JpaRepository<ReviewHelpful, Long> {

    Optional<ReviewHelpful> findByReviewIdAndUserId(Long reviewId, Long userId);
    
    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
    
    void deleteByReviewIdAndUserId(Long reviewId, Long userId);
    
    long countByReviewId(Long reviewId);
}
