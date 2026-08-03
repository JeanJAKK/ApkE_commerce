package com.ecommerce.repository;

import com.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour les opérations sur les articles de commande
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);
    
    @Query("SELECT oi.product.id, SUM(oi.quantity) as totalSold FROM OrderItem oi " +
           "GROUP BY oi.product.id ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts();
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id = :productId")
    List<OrderItem> findByProductId(@Param("productId") Long productId);
}
