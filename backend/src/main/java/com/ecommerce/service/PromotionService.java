package com.ecommerce.service;

import com.ecommerce.dto.request.PromotionRequest;
import com.ecommerce.dto.response.PromotionResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Promotion;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.PromotionMapper;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service pour la gestion des promotions
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PromotionMapper promotionMapper;

    /**
     * Créer une promotion
     */
    @Transactional
    public PromotionResponse create(PromotionRequest request) {
        if (promotionRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Ce code promo existe déjà");
        }

        Promotion promotion = promotionMapper.toEntity(request);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", request.getCategoryId()));
            promotion.setCategory(category);
        }

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", request.getProductId()));
            promotion.setProduct(product);
        }

        promotion = promotionRepository.save(promotion);
        log.info("Promotion créée: {} ({})", promotion.getTitle(), promotion.getCode());

        return promotionMapper.toResponse(promotion);
    }

    /**
     * Mettre à jour une promotion
     */
    @Transactional
    public PromotionResponse update(Long id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion", "id", id));

        if (!promotion.getCode().equals(request.getCode()) && promotionRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Ce code promo existe déjà");
        }

        promotionMapper.updateEntity(request, promotion);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie", "id", request.getCategoryId()));
            promotion.setCategory(category);
        } else {
            promotion.setCategory(null);
        }

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", request.getProductId()));
            promotion.setProduct(product);
        } else {
            promotion.setProduct(null);
        }

        promotion = promotionRepository.save(promotion);
        log.info("Promotion mise à jour: {} ({})", promotion.getTitle(), promotion.getCode());

        return promotionMapper.toResponse(promotion);
    }

    /**
     * Supprimer une promotion
     */
    @Transactional
    public void delete(Long id) {
        Promotion promotion = promotionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion", "id", id));

        promotionRepository.delete(promotion);
        log.info("Promotion supprimée: {} ({})", promotion.getTitle(), promotion.getCode());
    }

    /**
     * Récupérer une promotion par ID
     */
    @Transactional(readOnly = true)
    public PromotionResponse getById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion", "id", id));
        return promotionMapper.toResponse(promotion);
    }

    /**
     * Récupérer une promotion par code
     */
    @Transactional(readOnly = true)
    public PromotionResponse getByCode(String code) {
        Promotion promotion = promotionRepository.findByCode(code)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion", "code", code));
        return promotionMapper.toResponse(promotion);
    }

    /**
     * Récupérer une promotion valide par code
     */
    @Transactional(readOnly = true)
    public PromotionResponse getValidPromotion(String code) {
        Promotion promotion = promotionRepository.findValidPromotion(code, LocalDateTime.now())
            .orElseThrow(() -> new BadRequestException("Code promo invalide ou expiré"));
        return promotionMapper.toResponse(promotion);
    }

    /**
     * Récupérer toutes les promotions
     */
    @Transactional(readOnly = true)
    public List<PromotionResponse> getAll() {
        return promotionMapper.toResponseList(promotionRepository.findAll());
    }

    /**
     * Récupérer les promotions actives
     */
    @Transactional(readOnly = true)
    public List<PromotionResponse> getActive() {
        return promotionMapper.toResponseList(promotionRepository.findActivePromotions(LocalDateTime.now()));
    }

    /**
     * Récupérer les promotions qui expirent bientôt
     */
    @Transactional(readOnly = true)
    public List<PromotionResponse> getExpiringSoon() {
        return promotionMapper.toResponseList(promotionRepository.findExpiringSoon());
    }

    /**
     * Activer/Désactiver une promotion
     */
    @Transactional
    public PromotionResponse toggleActive(Long id) {
        Promotion promotion = promotionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion", "id", id));

        promotion.setActive(!promotion.isActive());
        promotion = promotionRepository.save(promotion);

        log.info("Promotion {} {}", promotion.getCode(), promotion.isActive() ? "activée" : "désactivée");

        return promotionMapper.toResponse(promotion);
    }

    /**
     * Incrémenter le compteur d'utilisation
     */
    @Transactional
    public void incrementUsage(Long id) {
        Promotion promotion = promotionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Promotion", "id", id));

        promotion.setUsedCount(promotion.getUsedCount() + 1);
        promotionRepository.save(promotion);
    }
}
