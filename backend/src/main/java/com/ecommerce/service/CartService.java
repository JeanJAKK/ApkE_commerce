package com.ecommerce.service;

import com.ecommerce.dto.request.CartItemRequest;
import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Promotion;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.CartMapper;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.PromotionRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion du panier
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PromotionRepository promotionRepository;
    private final SiteSettingsService siteSettingsService;
    private final CartMapper cartMapper;

    /**
     * Récupérer le panier d'un utilisateur
     */
    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        SiteSettingsResponse settings = siteSettingsService.getSettings();

        List<CartItemResponse> itemResponses = items.stream()
            .map(cartMapper::toItemResponse)
            .collect(Collectors.toList());

        int totalItems = items.size();
        int totalQuantity = items.stream().mapToInt(CartItem::getQuantity).sum();

        BigDecimal subtotal = items.stream()
            .map(CartItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean freeShipping = settings.isFreeShippingEnabled() &&
            subtotal.compareTo(BigDecimal.valueOf(settings.getFreeShippingThreshold())) >= 0;

        BigDecimal shippingCost = freeShipping ? BigDecimal.ZERO :
            BigDecimal.valueOf(settings.getDefaultShippingCost());

        BigDecimal tax = BigDecimal.ZERO;
        if (settings.getTaxRate() > 0) {
            tax = subtotal.multiply(BigDecimal.valueOf(settings.getTaxRate() / 100));
        }

        return CartResponse.builder()
            .items(itemResponses)
            .totalItems(totalItems)
            .totalQuantity(totalQuantity)
            .subtotal(subtotal)
            .shippingCost(shippingCost)
            .tax(tax)
            .discount(BigDecimal.ZERO)
            .total(subtotal.add(shippingCost).add(tax))
            .freeShipping(freeShipping)
            .build();
    }

    /**
     * Ajouter un article au panier
     */
    @Transactional
    public CartResponse addItem(Long userId, CartItemRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", userId));

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", request.getProductId()));

        if (product.isOutOfStock()) {
            throw new BadRequestException("Ce produit n'est plus en stock");
        }

        // Vérifier si l'article existe déjà dans le panier
        CartItem existingItem = cartItemRepository.findByUserIdAndProductIdAndSelectedColorAndSelectedSize(
            userId, request.getProductId(), request.getSelectedColor(), request.getSelectedSize()
        ).orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (newQuantity > product.getStock()) {
                throw new BadRequestException("Stock insuffisant. Disponible: " + product.getStock());
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
            log.info("Quantité mise à jour dans le panier: {} x {}", newQuantity, product.getName());
        } else {
            if (request.getQuantity() > product.getStock()) {
                throw new BadRequestException("Stock insuffisant. Disponible: " + product.getStock());
            }

            CartItem cartItem = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .selectedColor(request.getSelectedColor())
                .selectedSize(request.getSelectedSize())
                .build();

            cartItemRepository.save(cartItem);
            log.info("Article ajouté au panier: {} x {}", request.getQuantity(), product.getName());
        }

        return getCart(userId);
    }

    /**
     * Mettre à jour la quantité d'un article
     */
    @Transactional
    public CartResponse updateItem(Long userId, Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", itemId));

        if (!item.getUser().getId().equals(userId)) {
            throw new BadRequestException("Cet article ne vous appartient pas");
        }

        if (quantity <= 0) {
            return removeItem(userId, itemId);
        }

        if (quantity > item.getProduct().getStock()) {
            throw new BadRequestException("Stock insuffisant. Disponible: " + item.getProduct().getStock());
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        log.info("Quantité mise à jour: {} x {}", quantity, item.getProduct().getName());

        return getCart(userId);
    }

    /**
     * Supprimer un article du panier
     */
    @Transactional
    public CartResponse removeItem(Long userId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", itemId));

        if (!item.getUser().getId().equals(userId)) {
            throw new BadRequestException("Cet article ne vous appartient pas");
        }

        cartItemRepository.delete(item);
        log.info("Article supprimé du panier: {}", item.getProduct().getName());

        return getCart(userId);
    }

    /**
     * Vider le panier
     */
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
        log.info("Panier vidé pour l'utilisateur: {}", userId);
    }

    /**
     * Appliquer un code promo
     */
    @Transactional
    public CartResponse applyPromoCode(Long userId, String code) {
        Promotion promotion = promotionRepository.findValidPromotion(code, LocalDateTime.now())
            .orElseThrow(() -> new BadRequestException("Code promo invalide ou expiré"));

        CartResponse cart = getCart(userId);

        BigDecimal discount = promotion.calculateDiscount(cart.getSubtotal());
        BigDecimal newTotal = cart.getTotal().subtract(discount);

        if (newTotal.compareTo(BigDecimal.ZERO) < 0) {
            newTotal = BigDecimal.ZERO;
        }

        return CartResponse.builder()
            .items(cart.getItems())
            .totalItems(cart.getTotalItems())
            .totalQuantity(cart.getTotalQuantity())
            .subtotal(cart.getSubtotal())
            .shippingCost(cart.getShippingCost())
            .tax(cart.getTax())
            .discount(discount)
            .total(newTotal)
            .appliedPromoCode(code)
            .freeShipping(cart.isFreeShipping())
            .build();
    }
}
