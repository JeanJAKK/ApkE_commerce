import { apiRequest } from './api';
import type {
  ApiResponse,
  Product,
  PageResponse,
  SearchFilters,
} from '@/types';

// Produits
export const productService = {
  getAll: (page = 0, size = 20, sortBy = 'createdAt', sortDir = 'desc') =>
    apiRequest.get<ApiResponse<PageResponse<Product>>>('/products', {
      page,
      size,
      sortBy,
      sortDir,
    }),

  getById: (id: number) =>
    apiRequest.get<ApiResponse<Product>>(`/products/${id}`),

  getBySlug: (slug: string) =>
    apiRequest.get<ApiResponse<Product>>(`/products/slug/${slug}`),

  search: (filters: SearchFilters) =>
    apiRequest.post<ApiResponse<PageResponse<Product>>>('/products/search', filters),

  getFeatured: (limit = 8) =>
    apiRequest.get<ApiResponse<Product[]>>('/products/featured', { limit }),

  getNewArrivals: (limit = 8) =>
    apiRequest.get<ApiResponse<Product[]>>('/products/new-arrivals', { limit }),

  getOnSale: (limit = 8) =>
    apiRequest.get<ApiResponse<Product[]>>('/products/on-sale', { limit }),

  getSimilar: (id: number, limit = 4) =>
    apiRequest.get<ApiResponse<Product[]>>(`/products/${id}/similar`, { limit }),

  getByCategory: (categoryId: number, limit = 20) =>
    apiRequest.get<ApiResponse<Product[]>>(`/products/category/${categoryId}`, { limit }),

  getOutOfStock: () =>
    apiRequest.get<ApiResponse<Product[]>>('/products/out-of-stock'),

  getLowStock: (threshold = 10) =>
    apiRequest.get<ApiResponse<Product[]>>('/products/low-stock', { threshold }),
};

// Catégories
export const categoryService = {
  getAll: () =>
    apiRequest.get<ApiResponse<import('@/types').Category[]>>('/categories'),

  getMain: () =>
    apiRequest.get<ApiResponse<import('@/types').Category[]>>('/categories/main'),

  getById: (id: number) =>
    apiRequest.get<ApiResponse<import('@/types').Category>>(`/categories/${id}`),

  getBySlug: (slug: string) =>
    apiRequest.get<ApiResponse<import('@/types').Category>>(`/categories/slug/${slug}`),

  getSubcategories: (parentId: number) =>
    apiRequest.get<ApiResponse<import('@/types').Category[]>>(`/categories/${parentId}/subcategories`),
};

// Avis
export const reviewService = {
  getByProduct: (productId: number, page = 0, size = 10) =>
    apiRequest.get<ApiResponse<PageResponse<import('@/types').Review>>>(
      `/reviews/product/${productId}`,
      { page, size }
    ),

  getFeatured: () =>
    apiRequest.get<ApiResponse<import('@/types').Review[]>>('/reviews/featured'),

  getDistribution: (productId: number) =>
    apiRequest.get<ApiResponse<{ [key: number]: number }>>(`/reviews/product/${productId}/distribution`),
};

// Commentaires
export const commentService = {
  getByProduct: (productId: number) =>
    apiRequest.get<ApiResponse<import('@/types').Comment[]>>(`/comments/product/${productId}`),
};

// Favoris
export const favoriteService = {
  getUserFavorites: (userId: number, page = 0, size = 20) =>
    apiRequest.get<ApiResponse<PageResponse<Product>>>('/favorites', { userId, page, size }),

  getUserFavoritesList: (userId: number) =>
    apiRequest.get<ApiResponse<Product[]>>('/favorites/list', { userId }),

  isFavorite: (userId: number, productId: number) =>
    apiRequest.get<ApiResponse<boolean>>('/favorites/check', { userId, productId }),

  add: (userId: number, productId: number) =>
    apiRequest.post<ApiResponse<null>>('/favorites', null, { params: { userId, productId } }),

  remove: (userId: number, productId: number) =>
    apiRequest.delete<ApiResponse<null>>('/favorites', { params: { userId, productId } }),
};

// Promotions
export const promotionService = {
  getActive: () =>
    apiRequest.get<ApiResponse<import('@/types').Promotion[]>>('/promotions/active'),

  validate: (code: string) =>
    apiRequest.get<ApiResponse<import('@/types').Promotion>>(`/promotions/code/${code}`),
};
