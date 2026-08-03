package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * DTO pour la réponse du tableau de bord
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private BigDecimal todaySales;
    private BigDecimal monthlySales;
    private BigDecimal totalRevenue;
    private long todayOrders;
    private long monthlyOrders;
    private long totalOrders;
    private long totalCustomers;
    private long totalProducts;
    private long lowStockProducts;
    private long outOfStockProducts;
    private long pendingOrders;
    private List<OrderResponse> recentOrders;
    private List<ProductResponse> popularProducts;
    private List<NotificationResponse> recentNotifications;
    private List<Map<String, Object>> salesChart;
    private List<Map<String, Object>> ordersChart;
    private Map<Integer, Long> ratingDistribution;
}
