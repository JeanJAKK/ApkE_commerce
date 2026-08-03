package com.ecommerce.service;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.request.SearchRequest;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des produits
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    /**
     * Créer un nouveau produit
     */
    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new BadRequestException("Ce SKU existe déjà");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", request.getCategoryId()));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);

        if (request.getSubcategoryId() != null) {
            Category subcategory = categoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Sous-catégorie", "id", request.getSubcategoryId()));
            product.setSubcategory(subcategory);
        }

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            product.getImages().addAll(request.getImages());
        }

        product = productRepository.save(product);
        log.info("Produit créé: {}", product.getName());

        return productMapper.toResponse(product);
    }

    /**
     * Mettre à jour un produit
     */
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        if (!product.getSku().equals(request.getSku()) && productRepository.existsBySku(request.getSku())) {
            throw new BadRequestException("Ce SKU existe déjà");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", request.getCategoryId()));

        productMapper.updateEntity(request, product);
        product.setCategory(category);

        if (request.getSubcategoryId() != null) {
            Category subcategory = categoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Sous-catégorie", "id", request.getSubcategoryId()));
            product.setSubcategory(subcategory);
        } else {
            product.setSubcategory(null);
        }

        if (request.getImages() != null) {
            product.getImages().clear();
            product.getImages().addAll(request.getImages());
        }

        product = productRepository.save(product);
        log.info("Produit mis à jour: {}", product.getName());

        return productMapper.toResponse(product);
    }

    /**
     * Supprimer un produit
     */
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        productRepository.delete(product);
        log.info("Produit supprimé: {}", product.getName());
    }

    /**
     * Récupérer un produit par ID
     */
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        // Incrémenter le nombre de vues
        productRepository.incrementViewCount(id);

        return productMapper.toResponse(product);
    }

    /**
     * Récupérer un produit par slug
     */
    @Transactional(readOnly = true)
    public ProductResponse getBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "slug", slug));

        // Incrémenter le nombre de vues
        productRepository.incrementViewCount(product.getId());

        return productMapper.toResponse(product);
    }

    /**
     * Récupérer tous les produits paginés
     */
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.findAll(pageable);

        return PageResponse.of(products.map(productMapper::toResponse));
    }

    /**
     * Rechercher des produits
     */
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> search(SearchRequest request) {
        Specification<Product> spec = ProductSpecification.buildSpecification(request);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (request.getSortBy() != null) {
            sort = request.getSortOrder() != null && request.getSortOrder().equalsIgnoreCase("asc")
                ? Sort.by(request.getSortBy()).ascending()
                : Sort.by(request.getSortBy()).descending();
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Page<Product> products = productRepository.findAll(spec, pageable);

        return PageResponse.of(products.map(productMapper::toResponse));
    }

    /**
     * Récupérer les produits vedettes
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getFeatured(int limit) {
        return productMapper.toResponseList(
            productRepository.findFeaturedProducts(PageRequest.of(0, limit))
        );
    }

    /**
     * Récupérer les nouveautés
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getNewArrivals(int limit) {
        return productMapper.toResponseList(
            productRepository.findNewArrivals(PageRequest.of(0, limit))
        );
    }

    /**
     * Récupérer les produits en promotion
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getOnSale(int limit) {
        return productMapper.toResponseList(
            productRepository.findOnSaleProducts(PageRequest.of(0, limit))
        );
    }

    /**
     * Récupérer les produits similaires
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getSimilar(Long productId, int limit) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", productId));

        return productMapper.toResponseList(
            productRepository.findSimilarProducts(product.getCategory().getId(), productId, PageRequest.of(0, limit))
        );
    }

    /**
     * Récupérer les produits d'une catégorie
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getByCategory(Long categoryId, int limit) {
        return productMapper.toResponseList(
            productRepository.findByCategoryId(categoryId).stream().limit(limit).toList()
        );
    }

    /**
     * Archiver un produit
     */
    @Transactional
    public void archive(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        product.setArchived(true);
        productRepository.save(product);

        log.info("Produit archivé: {}", product.getName());
    }

    /**
     * Dupliquer un produit
     */
    @Transactional
    public ProductResponse duplicate(Long id) {
        Product original = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        Product duplicate = Product.builder()
            .name(original.getName() + " (Copie)")
            .slug(original.getSlug() + "-copy-" + System.currentTimeMillis())
            .description(original.getDescription())
            .shortDescription(original.getShortDescription())
            .price(original.getPrice())
            .oldPrice(original.getOldPrice())
            .discountPercent(original.getDiscountPercent())
            .stock(original.getStock())
            .sku(original.getSku() + "-COPY")
            .brand(original.getBrand())
            .colors(original.getColors())
            .sizes(original.getSizes())
            .images(original.getImages())
            .specifications(original.getSpecifications())
            .featured(false)
            .newArrival(false)
            .onSale(false)
            .active(true)
            .archived(false)
            .category(original.getCategory())
            .subcategory(original.getSubcategory())
            .build();

        duplicate = productRepository.save(duplicate);
        log.info("Produit dupliqué: {} -> {}", original.getName(), duplicate.getName());

        return productMapper.toResponse(duplicate);
    }

    /**
     * Récupérer les produits en rupture de stock
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getOutOfStock() {
        return productMapper.toResponseList(productRepository.findOutOfStockProducts());
    }

    /**
     * Récupérer les produits à faible stock
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getLowStock(int threshold) {
        return productMapper.toResponseList(productRepository.findLowStockProducts(threshold));
    }

    /**
     * Mettre à jour le stock
     */
    @Transactional
    public void updateStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));

        product.setStock(quantity);
        productRepository.save(product);

        log.info("Stock mis à jour pour {}: {}", product.getName(), quantity);
    }
}
