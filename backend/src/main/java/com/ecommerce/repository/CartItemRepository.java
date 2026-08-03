package com.ecommerce.repository;

import com.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les articles du panier
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserId(Long userId);
    
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    
    Optional<CartItem> findByUserIdAndProductIdAndSelectedColorAndSelectedSize(
        Long userId, Long productId, String color, String size);
    
    void deleteByUserId(Long userId);
    
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    @Query("SELECT COUNT(c) FROM CartItem c WHERE c.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT SUM(c.quantity) FROM CartItem c WHERE c.user.id = :userId")
    int getTotalQuantityByUserId(@Param("userId") Long userId);
}
