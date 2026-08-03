package com.ecommerce.repository;

import com.ecommerce.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour les opérations sur les notifications
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Page<Notification> findByUserId(Long userId, Pageable pageable);
    
    List<Notification> findByUserIdAndReadFalseOrderByCreatedAtDesc(Long userId);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.read = false")
    long countUnreadByUserId(@Param("userId") Long userId);
    
    @Modifying
    @Query("UPDATE Notification n SET n.read = true WHERE n.user.id = :userId")
    void markAllAsRead(@Param("userId") Long userId);
    
    @Query("SELECT n FROM Notification n ORDER BY n.createdAt DESC")
    Page<Notification> findRecentNotifications(Pageable pageable);
    
    @Query("SELECT n FROM Notification n WHERE n.read = false ORDER BY n.createdAt DESC")
    Page<Notification> findUnreadNotifications(Pageable pageable);
}
