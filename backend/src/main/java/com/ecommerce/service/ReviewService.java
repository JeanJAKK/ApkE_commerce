package com.ecommerce.service;

import com.ecommerce.dto.request.ReviewRequest;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.ReviewResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.mapper.ReviewMapper;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewHelpfulRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des avis
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewHelpfulRepository reviewHelpfulRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final ReviewMapper reviewMapper;

    /**
     * Créer un avis
     */
    @Transactional
    public ReviewResponse create(Long productId, Long userId, ReviewRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", productId));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        // Vérifier si l'utilisateur a déjà laissé un avis
        if (reviewRepository.existsByProductIdAndUserId(productId, userId)) {
            throw new BadRequestException("Vous avez déjà laissé un avis sur ce produit");
        }

        Review review = reviewMapper.toEntity(request);
        review.setProduct(product);
        review.setUser(user);
        review.setVerified(true); // À implémenter avec vérification d'achat réelle

        review = reviewRepository.save(review);
        log.info("Nouvel avis créé pour {} par {}", product.getName(), user.getFullName());

        // Notification pour l'administrateur
        notificationService.createReviewNotification(review);

        return reviewMapper.toResponse(review);
    }

    /**
     * Mettre à jour un avis
     */
    @Transactional
    public ReviewResponse update(Long id, Long userId, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Avis", "id", id));

        if (!review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Vous ne pouvez pas modifier cet avis");
        }

        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setContent(request.getContent());
        review.setImage(request.getImage());

        review = reviewRepository.save(review);
        log.info("Avis mis à jour: {}", review.getTitle());

        return reviewMapper.toResponse(review);
    }

    /**
     * Supprimer un avis
     */
    @Transactional
    public void delete(Long id, Long userId, boolean isAdmin) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Avis", "id", id));

        if (!isAdmin && !review.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Vous ne pouvez pas supprimer cet avis");
        }

        reviewRepository.delete(review);
        log.info("Avis supprimé: {}", review.getTitle());
    }

    /**
     * Récupérer les avis d'un produit
     */
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getByProduct(Long productId, int page, int size) {
        Page<Review> reviews = reviewRepository.findByProductId(productId, PageRequest.of(page, size));
        return PageResponse.of(reviews.map(reviewMapper::toResponse));
    }

    /**
     * Récupérer un avis par ID
     */
    @Transactional(readOnly = true)
    public ReviewResponse getById(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Avis", "id", id));
        return reviewMapper.toResponse(review);
    }

    /**
     * Marquer/démarquer un avis comme visible
     */
    @Transactional
    public ReviewResponse toggleVisibility(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Avis", "id", id));

        review.setVisible(!review.isVisible());
        review = reviewRepository.save(review);

        log.info("Avis {} {}", review.getTitle(), review.isVisible() ? "rendu visible" : "masqué");

        return reviewMapper.toResponse(review);
    }

    /**
     * Épingler/désépingler un avis
     */
    @Transactional
    public ReviewResponse toggleFeatured(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Avis", "id", id));

        review.setFeatured(!review.isFeatured());
        review = reviewRepository.save(review);

        log.info("Avis {} {}", review.getTitle(), review.isFeatured() ? "épinglé" : "désépinglé");

        return reviewMapper.toResponse(review);
    }

    /**
     * Voter "utile" pour un avis
     */
    @Transactional
    public void voteHelpful(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Avis", "id", reviewId));

        if (reviewHelpfulRepository.existsByReviewIdAndUserId(reviewId, userId)) {
            throw new BadRequestException("Vous avez déjà vote pour cet avis");
        }

        com.ecommerce.entity.ReviewHelpful vote = com.ecommerce.entity.ReviewHelpful.builder()
            .review(review)
            .user(userRepository.getReferenceById(userId))
            .helpful(true)
            .build();

        reviewHelpfulRepository.save(vote);
        log.info("Vote utile pour l'avis {}", reviewId);
    }

    /**
     * Supprimer un vote "utile"
     */
    @Transactional
    public void removeHelpfulVote(Long reviewId, Long userId) {
        reviewHelpfulRepository.deleteByReviewIdAndUserId(reviewId, userId);
        log.info("Vote utile supprimé pour l'avis {}", reviewId);
    }

    /**
     * Récupérer les avis mis en avant
     */
    @Transactional(readOnly = true)
    public List<ReviewResponse> getFeatured() {
        return reviewMapper.toResponseList(reviewRepository.findByFeaturedTrue());
    }

    /**
     * Récupérer les avis récents
     */
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getRecent(int page, int size) {
        Page<Review> reviews = reviewRepository.findRecentReviews(PageRequest.of(page, size));
        return PageResponse.of(reviews.map(reviewMapper::toResponse));
    }

    /**
     * Obtenir la distribution des notes
     */
    @Transactional(readOnly = true)
    public java.util.Map<Integer, Long> getRatingDistribution(Long productId) {
        List<Object[]> results = reviewRepository.getRatingDistribution(productId);
        java.util.Map<Integer, Long> distribution = new java.util.HashMap<>();

        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0L);
        }

        for (Object[] result : results) {
            int rating = (Integer) result[0];
            long count = (Long) result[1];
            distribution.put(rating, count);
        }

        return distribution;
    }
}
