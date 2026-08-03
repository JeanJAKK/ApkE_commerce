package com.ecommerce.repository;

import com.ecommerce.entity.EOrderStatus;
import com.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les commandes
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByUserId(Long userId);
    
    Page<Order> findByStatus(EOrderStatus status, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    Page<Order> findByUserIdPaginated(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByStatusAndDateRange(@Param("status") EOrderStatus status,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') " +
           "AND o.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalByDateRange(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') " +
           "AND o.createdAt >= :startDate")
    BigDecimal sumTotalSince(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status NOT IN ('CANCELLED', 'REFUNDED') " +
           "AND o.createdAt BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    long countByStatus(@Param("status") EOrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.createdAt DESC")
    Page<Order> findRecentByStatus(@Param("status") EOrderStatus status, Pageable pageable);
    
    @Query("SELECT o FROM Order o ORDER BY o.createdAt DESC")
    Page<Order> findAllOrders(Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.customerEmail LIKE %:query% " +
           "OR o.orderNumber LIKE %:query% " +
           "OR o.customerName LIKE %:query% " +
           "OR o.customerPhone LIKE %:query%")
    Page<Order> searchOrders(@Param("query") String query, Pageable pageable);
    
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") EOrderStatus status);
}
