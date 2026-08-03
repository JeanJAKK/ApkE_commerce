package com.ecommerce.repository;

import com.ecommerce.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les favoris
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserId(Long userId);
    
    Page<Favorite> findByUserId(Long userId, Pageable pageable);
    
    Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);
    
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.product.id = :productId")
    long countByProductId(@Param("productId") Long productId);
}
