package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour les opérations sur les produits
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findBySlug(String slug);
    
    boolean existsBySku(String sku);
    
    boolean existsBySlug(String slug);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false ORDER BY p.createdAt DESC")
    List<Product> findLatestProducts(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false AND p.featured = true")
    List<Product> findFeaturedProducts(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false AND p.newArrival = true")
    List<Product> findNewArrivals(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false AND p.onSale = true")
    List<Product> findOnSaleProducts(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false AND p.stock <= :threshold")
    List<Product> findLowStockProducts(@Param("threshold") int threshold);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false AND p.stock = 0")
    List<Product> findOutOfStockProducts();
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.active = true AND p.archived = false")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT p FROM Product p WHERE p.subcategory.id = :subcategoryId AND p.active = true AND p.archived = false")
    List<Product> findBySubcategoryId(@Param("subcategoryId") Long subcategoryId);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false " +
           "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Product> searchProducts(@Param("query") String query, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false " +
           "AND p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                    @Param("maxPrice") BigDecimal maxPrice, 
                                    Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false " +
           "ORDER BY p.soldCount DESC")
    List<Product> findBestSellers(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false " +
           "ORDER BY p.viewCount DESC")
    List<Product> findMostViewed(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.archived = false " +
           "AND p.category.id = :categoryId AND p.id != :excludeId " +
           "ORDER BY RANDOM()")
    List<Product> findSimilarProducts(@Param("categoryId") Long categoryId, 
                                      @Param("excludeId") Long excludeId, 
                                      Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.active = true AND p.archived = false")
    long countActiveProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.active = true AND p.archived = false AND p.stock = 0")
    long countOutOfStockProducts();
    
    @Modifying
    @Query("UPDATE Product p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);
    
    @Modifying
    @Query("UPDATE Product p SET p.soldCount = p.soldCount + :quantity WHERE p.id = :id")
    void incrementSoldCount(@Param("id") Long id, @Param("quantity") int quantity);
}
