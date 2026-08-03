package com.ecommerce.service;

import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Favorite;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.FavoriteRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des favoris
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    /**
     * Ajouter un produit aux favoris
     */
    @Transactional
    public boolean addFavorite(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", productId));

        if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            log.info("Le produit {} est déjà dans les favoris de l'utilisateur {}", productId, userId);
            return false;
        }

        Favorite favorite = Favorite.builder()
            .user(user)
            .product(product)
            .build();

        favoriteRepository.save(favorite);
        log.info("Produit {} ajouté aux favoris de {}", productId, userId);

        return true;
    }

    /**
     * Retirer un produit des favoris
     */
    @Transactional
    public boolean removeFavorite(Long userId, Long productId) {
        if (!favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            log.info("Le produit {} n'est pas dans les favoris de l'utilisateur {}", productId, userId);
            return false;
        }

        favoriteRepository.deleteByUserIdAndProductId(userId, productId);
        log.info("Produit {} retiré des favoris de {}", productId, userId);

        return true;
    }

    /**
     * Vérifier si un produit est en favori
     */
    @Transactional(readOnly = true)
    public boolean isFavorite(Long userId, Long productId) {
        return favoriteRepository.existsByUserIdAndProductId(userId, productId);
    }

    /**
     * Récupérer les favoris d'un utilisateur
     */
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getUserFavorites(Long userId, int page, int size) {
        Page<Product> favorites = favoriteRepository.findByUserId(userId, PageRequest.of(page, size))
            .map(Favorite::getProduct);

        return PageResponse.of(favorites.map(productMapper::toResponse));
    }

    /**
     * Récupérer les favoris d'un utilisateur (liste)
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getUserFavoritesList(Long userId) {
        return favoriteRepository.findByUserId(userId).stream()
            .map(fav -> productMapper.toResponse(fav.getProduct()))
            .toList();
    }

    /**
     * Compter les favoris d'un utilisateur
     */
    @Transactional(readOnly = true)
    public long countUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId).size();
    }

    /**
     * Compter les favoris d'un produit
     */
    @Transactional(readOnly = true)
    public long countProductFavorites(Long productId) {
        return favoriteRepository.countByProductId(productId);
    }
}
