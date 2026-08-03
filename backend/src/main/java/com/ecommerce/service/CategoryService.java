package com.ecommerce.service;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des catégories
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Créer une nouvelle catégorie
     */
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException("Cette catégorie existe déjà");
        }

        Category category = categoryMapper.toEntity(request);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie parente", "id", request.getParentId()));
            category.setParent(parent);
        }

        category = categoryRepository.save(category);
        log.info("Catégorie créée: {}", category.getName());

        return categoryMapper.toResponse(category);
    }

    /**
     * Mettre à jour une catégorie
     */
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", id));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new BadRequestException("Cette catégorie existe déjà");
        }

        categoryMapper.updateEntity(request, category);

        if (request.getParentId() != null) {
            if (request.getParentId().equals(id)) {
                throw new BadRequestException("Une catégorie ne peut pas être sa propre catégorie parente");
            }
            Category parent = categoryRepository.findById(request.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie parente", "id", request.getParentId()));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        category = categoryRepository.save(category);
        log.info("Catégorie mise à jour: {}", category.getName());

        return categoryMapper.toResponse(category);
    }

    /**
     * Supprimer une catégorie
     */
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", id));

        if (!category.getSubcategories().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer une catégorie avec des sous-catégories");
        }

        if (!category.getProducts().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer une catégorie avec des produits. Archivez d'abord les produits.");
        }

        categoryRepository.delete(category);
        log.info("Catégorie supprimée: {}", category.getName());
    }

    /**
     * Récupérer une catégorie par ID
     */
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", id));

        return categoryMapper.toResponse(category);
    }

    /**
     * Récupérer une catégorie par slug
     */
    @Transactional(readOnly = true)
    public CategoryResponse getBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "slug", slug));

        return categoryMapper.toResponse(category);
    }

    /**
     * Récupérer toutes les catégories principales
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getMainCategories() {
        return categoryMapper.toResponseList(categoryRepository.findMainCategories());
    }

    /**
     * Récupérer les sous-catégories d'une catégorie
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubcategories(Long parentId) {
        return categoryMapper.toResponseList(categoryRepository.findSubcategories(parentId));
    }

    /**
     * Récupérer toutes les catégories actives
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllActive() {
        return categoryMapper.toResponseList(categoryRepository.findAllActiveCategories());
    }

    /**
     * Récupérer toutes les catégories avec leurs sous-catégories
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllWithSubcategories() {
        List<Category> rootCategories = categoryRepository.findRootCategories();
        return categoryMapper.toResponseList(rootCategories);
    }

    /**
     * Réorganiser les catégories
     */
    @Transactional
    public void reorder(List<Long> categoryIds) {
        for (int i = 0; i < categoryIds.size(); i++) {
            Category category = categoryRepository.findById(categoryIds.get(i))
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", categoryIds.get(i)));
            category.setPosition(i + 1);
            categoryRepository.save(category);
        }
        log.info("Catégories réorganisées");
    }

    /**
     * Activer/Désactiver une catégorie
     */
    @Transactional
    public void toggleActive(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", id));

        category.setActive(!category.isActive());
        categoryRepository.save(category);

        log.info("Catégorie {} {}", category.getName(), category.isActive() ? "activée" : "désactivée");
    }
}
