package com.ecommerce.service;

import com.ecommerce.dto.request.OrderItemRequest;
import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.entity.*;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.repository.*;
import com.ecommerce.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des commandes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PromotionRepository promotionRepository;
    private final StockMovementRepository stockMovementRepository;
    private final NotificationService notificationService;
    private final SiteSettingsService siteSettingsService;
    private final OrderMapper orderMapper;

    @Value("${app.base-url:http://localhost:5173}")
    private String appBaseUrl;

    /**
     * Créer une commande
     */
    @Transactional
    public OrderResponse create(OrderRequest request, Long userId) {
        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;

        Order order = Order.builder()
            .orderNumber(generateOrderNumber())
            .user(user)
            .customerName(request.getCustomerName())
            .customerEmail(request.getCustomerEmail())
            .customerPhone(request.getCustomerPhone())
            .shippingAddress(request.getShippingAddress())
            .shippingCity(request.getShippingCity())
            .shippingCountry(request.getShippingCountry())
            .paymentMethod(request.getPaymentMethod())
            .paymentStatus(EPaymentStatus.PENDING)
            .status(EOrderStatus.PENDING)
            .notes(request.getNotes())
            .build();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", itemRequest.getProductId()));

            if (product.isOutOfStock()) {
                throw new BadRequestException("Le produit " + product.getName() + " n'est plus en stock");
            }

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new BadRequestException("Stock insuffisant pour " + product.getName());
            }

            BigDecimal itemPrice = product.getDiscountedPrice();
            BigDecimal itemTotal = itemPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem item = OrderItem.builder()
                .product(product)
                .quantity(itemRequest.getQuantity())
                .unitPrice(itemPrice)
                .totalPrice(itemTotal)
                .selectedColor(itemRequest.getSelectedColor())
                .selectedSize(itemRequest.getSelectedSize())
                .productName(product.getName())
                .productImage(product.getImages().isEmpty() ? null : product.getImages().get(0))
                .build();

            order.addItem(item);
            subtotal = subtotal.add(itemTotal);

            // Réduire le stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            // Enregistrer le mouvement de stock
            StockMovement movement = StockMovement.builder()
                .product(product)
                .quantityChange(-itemRequest.getQuantity())
                .stockBefore(product.getStock() + itemRequest.getQuantity())
                .stockAfter(product.getStock())
                .type(EStockMovementType.SALE)
                .reason("Vente - Commande " + order.getOrderNumber())
                .order(order)
                .build();
            stockMovementRepository.save(movement);
        }

        // Calculer les frais de livraison
        SiteSettingsResponse settings = siteSettingsService.getSettings();
        BigDecimal shippingCost = calculateShippingCost(subtotal, settings);
        BigDecimal discount = BigDecimal.ZERO;

        // Appliquer le code promo si présent
        if (request.getPromoCode() != null && !request.getPromoCode().isBlank()) {
            Promotion promotion = promotionRepository.findValidPromotion(
                request.getPromoCode(), LocalDateTime.now()
            ).orElseThrow(() -> new BadRequestException("Code promo invalide ou expiré"));

            discount = promotion.calculateDiscount(subtotal);
            order.setPromoCode(request.getPromoCode());
        }

        // Calculer la taxe
        BigDecimal tax = BigDecimal.ZERO;
        if (settings.getTaxRate() > 0) {
            BigDecimal taxableAmount = subtotal.add(shippingCost).subtract(discount);
            tax = taxableAmount.multiply(BigDecimal.valueOf(settings.getTaxRate() / 100));
        }

        BigDecimal total = subtotal.add(shippingCost).add(tax).subtract(discount);

        order.setSubtotal(subtotal);
        order.setShippingCost(shippingCost);
        order.setTax(tax);
        order.setDiscount(discount);
        order.setTotal(total);

        order = orderRepository.save(order);

        // Mettre à jour le compteur de vente des produits
        for (OrderItem item : order.getItems()) {
            productRepository.incrementSoldCount(item.getProduct().getId(), item.getQuantity());
        }

        // Créer une notification pour l'administrateur
        notificationService.createOrderNotification(order);

        log.info("Commande créée: {} - Total: {} {}", order.getOrderNumber(), total, settings.getCurrencySymbol());

        return orderMapper.toResponse(order);
    }

    /**
     * Récupérer une commande par ID
     */
    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));

        return orderMapper.toResponse(order);
    }

    /**
     * Récupérer une commande par numéro
     */
    @Transactional(readOnly = true)
    public OrderResponse getByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", "numéro", orderNumber));

        return orderMapper.toResponse(order);
    }

    /**
     * Récupérer les commandes d'un utilisateur
     */
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getByUserId(Long userId, int page, int size) {
        Page<Order> orders = orderRepository.findByUserIdPaginated(userId, PageRequest.of(page, size));
        return PageResponse.of(orders.map(orderMapper::toResponse));
    }

    /**
     * Rechercher des commandes
     */
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> search(String query, int page, int size) {
        Page<Order> orders = orderRepository.searchOrders(query, PageRequest.of(page, size));
        return PageResponse.of(orders.map(orderMapper::toResponse));
    }

    /**
     * Filtrer les commandes
     */
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> filter(
            EOrderStatus status, LocalDate startDate, LocalDate endDate, int page, int size) {

        Specification<Order> spec = OrderSpecification.buildSpecification(status, startDate, endDate);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Order> orders = orderRepository.findAll(spec, pageable);

        return PageResponse.of(orders.map(orderMapper::toResponse));
    }

    /**
     * Mettre à jour le statut d'une commande
     */
    @Transactional
    public OrderResponse updateStatus(Long id, EOrderStatus newStatus, String notes, Long adminId) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));

        EOrderStatus oldStatus = order.getStatus();

        if (oldStatus == EOrderStatus.DELIVERED || oldStatus == EOrderStatus.CANCELLED) {
            throw new BadRequestException("Impossible de modifier le statut d'une commande livrée ou annulée");
        }

        // Si annulation, restaurer le stock
        if (newStatus == EOrderStatus.CANCELLED && oldStatus != EOrderStatus.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);

                StockMovement movement = StockMovement.builder()
                    .product(product)
                    .quantityChange(item.getQuantity())
                    .stockBefore(product.getStock() - item.getQuantity())
                    .stockAfter(product.getStock())
                    .type(EStockMovementType.RETURN)
                    .reason("Annulation commande " + order.getOrderNumber())
                    .order(order)
                    .build();
                stockMovementRepository.save(movement);
            }
        }

        order.setStatus(newStatus);

        // Enregistrer l'historique
        User admin = adminId != null ? userRepository.findById(adminId).orElse(null) : null;
        OrderStatusHistory history = OrderStatusHistory.builder()
            .order(order)
            .oldStatus(oldStatus)
            .newStatus(newStatus)
            .changedBy(admin)
            .notes(notes)
            .build();
        order.getStatusHistory().add(history);

        order = orderRepository.save(order);
        log.info("Commande {}: statut {} -> {}", order.getOrderNumber(), oldStatus, newStatus);

        // Notifications
        notificationService.createStatusUpdateNotification(order, oldStatus, newStatus);

        return orderMapper.toResponse(order);
    }

    /**
     * Ajouter un numéro de suivi
     */
    @Transactional
    public OrderResponse addTrackingNumber(Long id, String trackingNumber) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commande", "id", id));

        order.setTrackingNumber(trackingNumber);
        order = orderRepository.save(order);

        log.info("Numéro de suivi ajouté pour {}: {}", order.getOrderNumber(), trackingNumber);

        return orderMapper.toResponse(order);
    }

    /**
     * Récupérer les commandes récentes
     */
    @Transactional(readOnly = true)
    public List<OrderResponse> getRecent(int limit) {
        return orderMapper.toResponseList(
            orderRepository.findAllOrders(PageRequest.of(0, limit)).getContent()
        );
    }

    /**
     * Récupérer les commandes en attente
     */
    @Transactional(readOnly = true)
    public List<OrderResponse> getPending(int limit) {
        return orderMapper.toResponseList(
            orderRepository.findRecentByStatus(EOrderStatus.PENDING, PageRequest.of(0, limit)).getContent()
        );
    }

    private String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD-" + date + "-" + uuid;
    }

    private BigDecimal calculateShippingCost(BigDecimal subtotal, SiteSettingsResponse settings) {
        if (settings.isFreeShippingEnabled() && subtotal.compareTo(BigDecimal.valueOf(settings.getFreeShippingThreshold())) >= 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(settings.getDefaultShippingCost());
    }
}
