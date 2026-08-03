package com.ecommerce.service;

import com.ecommerce.dto.response.DashboardResponse;
import com.ecommerce.dto.response.NotificationResponse;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service pour le tableau de bord administrateur
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReviewRepository reviewRepository;
    private final NotificationRepository notificationRepository;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final NotificationService notificationService;

    /**
     * Obtenir les statistiques du tableau de bord
     */
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(LocalTime.MAX);

        // Ventes
        BigDecimal todaySales = orderRepository.sumTotalByDateRange(startOfDay, endOfDay);
        BigDecimal monthlySales = orderRepository.sumTotalByDateRange(startOfMonth, endOfMonth);
        BigDecimal totalRevenue = orderRepository.sumTotalSince(LocalDateTime.of(2000, 1, 1, 0, 0));

        // Commandes
        long todayOrders = orderRepository.countByDateRange(startOfDay, endOfDay);
        long monthlyOrders = orderRepository.countByDateRange(startOfMonth, endOfMonth);
        long totalOrders = orderRepository.count();

        // Utilisateurs et produits
        long totalCustomers = userRepository.countActiveUsers();
        long totalProducts = productRepository.countActiveProducts();

        // Stock
        long lowStockProducts = productRepository.findLowStockProducts(10).size();
        long outOfStockProducts = productRepository.countOutOfStockProducts();
        long pendingOrders = orderRepository.countByStatus(com.ecommerce.entity.EOrderStatus.PENDING);

        // Commandes récentes
        List<Order> recentOrdersList = orderRepository.findAllOrders(PageRequest.of(0, 5)).getContent();
        List<OrderResponse> recentOrders = recentOrdersList.stream()
            .map(orderMapper::toResponse)
            .toList();

        // Produits populaires
        List<Object[]> bestSellers = orderItemRepository.findBestSellingProducts();
        List<Long> topProductIds = bestSellers.stream()
            .limit(5)
            .map(arr -> (Long) arr[0])
            .toList();
        List<Product> popularProductsList = topProductIds.isEmpty() ? 
            List.of() : productRepository.findAllById(topProductIds);
        List<ProductResponse> popularProducts = popularProductsList.stream()
            .map(productMapper::toResponse)
            .toList();

        // Notifications récentes
        List<NotificationResponse> recentNotifications = notificationService.getRecentForDashboard(5);

        // Données pour les graphiques
        List<Map<String, Object>> salesChart = getSalesChartData();
        List<Map<String, Object>> ordersChart = getOrdersChartData();
        Map<Integer, Long> ratingDistribution = getGlobalRatingDistribution();

        return DashboardResponse.builder()
            .todaySales(todaySales != null ? todaySales : BigDecimal.ZERO)
            .monthlySales(monthlySales != null ? monthlySales : BigDecimal.ZERO)
            .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
            .todayOrders(todayOrders)
            .monthlyOrders(monthlyOrders)
            .totalOrders(totalOrders)
            .totalCustomers(totalCustomers)
            .totalProducts(totalProducts)
            .lowStockProducts(lowStockProducts)
            .outOfStockProducts(outOfStockProducts)
            .pendingOrders(pendingOrders)
            .recentOrders(recentOrders)
            .popularProducts(popularProducts)
            .recentNotifications(recentNotifications)
            .salesChart(salesChart)
            .ordersChart(ordersChart)
            .ratingDistribution(ratingDistribution)
            .build();
    }

    /**
     * Obtenir les données du graphique des ventes (7 derniers jours)
     */
    private List<Map<String, Object>> getSalesChartData() {
        List<Map<String, Object>> chartData = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            BigDecimal sales = orderRepository.sumTotalByDateRange(startOfDay, endOfDay);

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("date", date.toString());
            dataPoint.put("sales", sales != null ? sales : BigDecimal.ZERO);
            chartData.add(dataPoint);
        }

        return chartData;
    }

    /**
     * Obtenir les données du graphique des commandes (7 derniers jours)
     */
    private List<Map<String, Object>> getOrdersChartData() {
        List<Map<String, Object>> chartData = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            long count = orderRepository.countByDateRange(startOfDay, endOfDay);

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("date", date.toString());
            dataPoint.put("orders", count);
            chartData.add(dataPoint);
        }

        return chartData;
    }

    /**
     * Obtenir la distribution globale des notes
     */
    private Map<Integer, Long> getGlobalRatingDistribution() {
        Map<Integer, Long> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0L);
        }
        // À implémenter avec une requête spécifique
        return distribution;
    }
}
