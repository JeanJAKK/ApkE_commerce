package com.ecommerce.repository;

import com.ecommerce.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour les opérations sur les mouvements de stock
 */
@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    Page<StockMovement> findByProductId(Long productId, Pageable pageable);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.product.id = :productId " +
           "AND sm.createdAt BETWEEN :startDate AND :endDate ORDER BY sm.createdAt DESC")
    List<StockMovement> findByProductIdAndDateRange(@Param("productId") Long productId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.type = :type ORDER BY sm.createdAt DESC")
    Page<StockMovement> findByType(@Param("type") com.ecommerce.entity.EStockMovementType type, 
                                    Pageable pageable);
}
