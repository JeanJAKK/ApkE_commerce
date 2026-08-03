package com.ecommerce.repository;

import com.ecommerce.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour les opérations sur les commentaires
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByProductIdAndParentIsNullOrderByCreatedAtDesc(Long productId);
    
    Page<Comment> findByProductId(Long productId, Pageable pageable);
    
    List<Comment> findByParentId(Long parentId);
    
    @Query("SELECT c FROM Comment c WHERE c.product.id = :productId AND c.visible = true " +
           "AND c.parent IS NULL ORDER BY c.pinned DESC, c.createdAt DESC")
    List<Comment> findVisibleCommentsByProductId(@Param("productId") Long productId);
    
    @Query("SELECT c FROM Comment c WHERE c.reported = true")
    Page<Comment> findReportedComments(Pageable pageable);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.visible = true AND c.parent IS NULL")
    long countTopLevelComments();
    
    @Query("SELECT c FROM Comment c WHERE c.product.id = :productId AND c.parent IS NULL " +
           "ORDER BY c.createdAt DESC")
    Page<Comment> findByProductIdPaginated(@Param("productId") Long productId, Pageable pageable);
}
