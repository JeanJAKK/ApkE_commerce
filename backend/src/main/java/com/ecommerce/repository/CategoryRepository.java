package com.ecommerce.repository;

import com.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les catégories
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    boolean existsByName(String name);
    
    @Query("SELECT c FROM Category c WHERE c.active = true AND c.parent IS NULL ORDER BY c.position ASC")
    List<Category> findMainCategories();
    
    @Query("SELECT c FROM Category c WHERE c.active = true AND c.parent.id = :parentId ORDER BY c.position ASC")
    List<Category> findSubcategories(@Param("parentId") Long parentId);
    
    @Query("SELECT c FROM Category c WHERE c.active = true ORDER BY c.position ASC")
    List<Category> findAllActiveCategories();
    
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.position ASC")
    List<Category> findRootCategories();
    
    @Query("SELECT c FROM Category c WHERE c.active = true AND c.icon IS NOT NULL")
    List<Category> findCategoriesWithIcon();
}
