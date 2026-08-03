package com.ecommerce.repository;

import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les utilisateurs
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByPhone(String phone);
    
    Optional<User> findByResetToken(String resetToken);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    @Query("SELECT u FROM User u WHERE u.blocked = false ORDER BY u.createdAt DESC")
    List<User> findActiveUsers();
    
    @Query("SELECT u FROM User u WHERE u.blocked = true")
    Page<User> findBlockedUsers(Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<User> searchUsers(@Param("query") String query, Pageable pageable);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.blocked = false")
    long countActiveUsers();
}
