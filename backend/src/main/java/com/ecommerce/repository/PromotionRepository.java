package com.ecommerce.repository;

import com.ecommerce.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les promotions
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByCode(String code);
    
    boolean existsByCode(String code);
    
    List<Promotion> findByActiveTrue();
    
    @Query("SELECT p FROM Promotion p WHERE p.active = true AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE p.code = :code AND p.active = true " +
           "AND p.startDate <= :now AND p.endDate >= :now " +
           "AND (p.usageLimit = 0 OR p.usedCount < p.usageLimit)")
    Optional<Promotion> findValidPromotion(@Param("code") String code, @Param("now") LocalDateTime now);
    
    List<Promotion> findByCategoryId(Long categoryId);
    
    List<Promotion> findByProductId(Long productId);
    
    @Query("SELECT p FROM Promotion p WHERE p.active = true ORDER BY p.endDate ASC")
    List<Promotion> findExpiringSoon();
}
