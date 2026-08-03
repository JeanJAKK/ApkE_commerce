package com.ecommerce.repository;

import com.ecommerce.entity.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour les opérations sur l'historique des statuts de commande
 */
@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {

    List<OrderStatusHistory> findByOrderIdOrderByChangedAtDesc(Long orderId);
}
