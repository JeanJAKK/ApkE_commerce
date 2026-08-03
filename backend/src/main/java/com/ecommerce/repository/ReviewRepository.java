package com.ecommerce.repository;

import com.ecommerce.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les avis
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);
    
    Page<Review> findByProductId(Long productId, Pageable pageable);
    
    Page<Review> findByUserId(Long userId, Pageable pageable);
    
    List<Review> findByFeaturedTrue();
    
    Page<Review> findByVisibleTrue(Pageable pageable);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.visible = true")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.visible = true")
    long countByProductId(@Param("productId") Long productId);
    
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.visible = true " +
           "GROUP BY r.rating")
    List<Object[]> getRatingDistribution(@Param("productId") Long productId);
    
    boolean existsByProductIdAndUserId(Long productId, Long userId);
    
    Optional<Review> findByProductIdAndUserId(Long productId, Long userId);
    
    @Query("SELECT r FROM Review r WHERE r.visible = true ORDER BY r.createdAt DESC")
    Page<Review> findRecentReviews(Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.verified = true AND r.visible = true ORDER BY r.helpfulVotes.size DESC")
    List<Review> findMostHelpfulReviews(Pageable pageable);
}
