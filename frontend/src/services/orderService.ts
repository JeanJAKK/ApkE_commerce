import { apiRequest } from './api';
import type { ApiResponse, Order, PageResponse, OrderStatus } from '@/types';

export const orderService = {
  create: (data: {
    customerName: string;
    customerEmail: string;
    customerPhone: string;
    shippingAddress: string;
    shippingCity?: string;
    shippingCountry?: string;
    paymentMethod: string;
    items: { productId: number; quantity: number; selectedColor?: string; selectedSize?: string }[];
    promoCode?: string;
    notes?: string;
  }, userId?: number) =>
    apiRequest.post<ApiResponse<Order>>('/orders', data, { params: userId ? { userId } : undefined }),

  getById: (id: number) =>
    apiRequest.get<ApiResponse<Order>>(`/orders/${id}`),

  getByOrderNumber: (orderNumber: string) =>
    apiRequest.get<ApiResponse<Order>>(`/orders/number/${orderNumber}`),

  getByUser: (userId: number, page = 0, size = 10) =>
    apiRequest.get<ApiResponse<PageResponse<Order>>>(`/orders/user/${userId}`, { page, size }),

  search: (query: string, page = 0, size = 10) =>
    apiRequest.get<ApiResponse<PageResponse<Order>>>('/orders/search', { query, page, size }),

  filter: (status?: OrderStatus, startDate?: string, endDate?: string, page = 0, size = 10) =>
    apiRequest.get<ApiResponse<PageResponse<Order>>>('/orders/filter', {
      status,
      startDate,
      endDate,
      page,
      size,
    }),

  getRecent: (limit = 5) =>
    apiRequest.get<ApiResponse<Order[]>>('/orders/recent', { limit }),

  getPending: (limit = 5) =>
    apiRequest.get<ApiResponse<Order[]>>('/orders/pending', { limit }),
};
