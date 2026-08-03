// Types pour l'application e-commerce

// Types d'authentification
export interface User {
  id: number;
  firstName: string;
  lastName: string;
  fullName: string;
  email: string;
  phone?: string;
  address?: string;
  city?: string;
  country?: string;
  avatar?: string;
  enabled: boolean;
  blocked: boolean;
  roles: string[];
  createdAt: string;
  updatedAt: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  user: User;
  roles: string[];
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone?: string;
  address?: string;
  city?: string;
  country?: string;
}

// Types de produit
export interface Product {
  id: number;
  name: string;
  slug: string;
  shortDescription?: string;
  description: string;
  specifications?: string;
  price: number;
  oldPrice?: number;
  discountedPrice: number;
  discountPercent?: number;
  stock: number;
  inStock: boolean;
  sku: string;
  brand?: string;
  colors: string[];
  sizes: string[];
  images: string[];
  mainImage?: string;
  featured: boolean;
  newArrival: boolean;
  onSale: boolean;
  active: boolean;
  viewCount: number;
  soldCount: number;
  categoryId: number;
  categoryName?: string;
  subcategoryId?: number;
  subcategoryName?: string;
  averageRating: number;
  reviewCount: number;
  isFavorite?: boolean;
  favoriteCount: number;
  createdAt: string;
  updatedAt: string;
}

export interface Category {
  id: number;
  name: string;
  slug: string;
  description?: string;
  image?: string;
  icon?: string;
  position: number;
  active: boolean;
  parentId?: number;
  parentName?: string;
  subcategories?: Category[];
  productCount: number;
}

// Types de commande
export interface OrderItem {
  id: number;
  productId: number;
  productName: string;
  productImage?: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
  selectedColor?: string;
  selectedSize?: string;
}

export interface Order {
  id: number;
  orderNumber: string;
  userId?: number;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  shippingAddress: string;
  shippingCity?: string;
  shippingCountry?: string;
  status: OrderStatus;
  statusDisplayName: string;
  paymentMethod: PaymentMethod;
  paymentMethodDisplayName: string;
  paymentStatus: PaymentStatus;
  subtotal: number;
  shippingCost: number;
  tax: number;
  discount: number;
  total: number;
  promoCode?: string;
  trackingNumber?: string;
  notes?: string;
  items: OrderItem[];
  itemCount: number;
  createdAt: string;
  updatedAt: string;
}

export enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  PREPARING = 'PREPARING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  REFUNDED = 'REFUNDED'
}

export enum PaymentMethod {
  CASH_ON_DELIVERY = 'CASH_ON_DELIVERY',
  MOBILE_MONEY = 'MOBILE_MONEY',
  CREDIT_CARD = 'CREDIT_CARD',
  PAYPAL = 'PAYPAL',
  BANK_TRANSFER = 'BANK_TRANSFER'
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  PAID = 'PAID',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED',
  PARTIALLY_REFUNDED = 'PARTIALLY_REFUNDED'
}

// Types de panier
export interface CartItem {
  id: number;
  productId: number;
  productName: string;
  productImage?: string;
  unitPrice: number;
  discountedPrice: number;
  quantity: number;
  totalPrice: number;
  selectedColor?: string;
  selectedSize?: string;
  availableStock: number;
  inStock: boolean;
  addedAt: string;
}

export interface Cart {
  items: CartItem[];
  totalItems: number;
  totalQuantity: number;
  subtotal: number;
  shippingCost: number;
  tax: number;
  discount: number;
  total: number;
  appliedPromoCode?: string;
  freeShipping: boolean;
}

// Types d'avis
export interface Review {
  id: number;
  productId: number;
  userId: number;
  userName: string;
  userAvatar?: string;
  rating: number;
  title: string;
  content: string;
  image?: string;
  verified: boolean;
  featured: boolean;
  visible: boolean;
  helpfulCount: number;
  createdAt: string;
}

// Types de commentaire
export interface Comment {
  id: number;
  content: string;
  productId: number;
  userId: number;
  userName: string;
  userAvatar?: string;
  parentId?: number;
  replies?: Comment[];
  visible: boolean;
  pinned: boolean;
  likeCount: number;
  attachment?: string;
  createdAt: string;
}

// Types de promotion
export interface Promotion {
  id: number;
  code: string;
  title: string;
  description?: string;
  type: PromotionType;
  typeDisplayName: string;
  discountValue?: number;
  discountPercent?: number;
  minimumOrderAmount?: number;
  maximumDiscountAmount?: number;
  usageLimit: number;
  usedCount: number;
  perUserLimit: number;
  startDate: string;
  endDate: string;
  active: boolean;
  valid: boolean;
  categoryId?: number;
  productId?: number;
}

export enum PromotionType {
  PERCENTAGE = 'PERCENTAGE',
  FIXED = 'FIXED',
  FREE_SHIPPING = 'FREE_SHIPPING',
  BUY_X_GET_Y = 'BUY_X_GET_Y',
  CATEGORY = 'CATEGORY',
  PRODUCT = 'PRODUCT'
}

// Types de paramètres du site
export interface SiteSettings {
  id: number;
  siteName: string;
  siteSlogan: string;
  logo?: string;
  favicon?: string;
  description?: string;
  primaryColor: string;
  secondaryColor: string;
  accentColor: string;
  darkModeEnabled: boolean;
  primaryFont: string;
  secondaryFont: string;
  heroBanner?: string;
  promoBanner1?: string;
  promoBanner2?: string;
  promoBanner3?: string;
  contactEmail: string;
  contactPhone: string;
  contactAddress: string;
  contactHours: string;
  facebookUrl?: string;
  twitterUrl?: string;
  instagramUrl?: string;
  youtubeUrl?: string;
  whatsappNumber?: string;
  telegramUrl?: string;
  privacyPolicy?: string;
  termsConditions?: string;
  refundPolicy?: string;
  maintenanceMode: boolean;
  maintenanceMessage?: string;
  freeShippingEnabled: boolean;
  freeShippingThreshold: number;
  defaultShippingCost: number;
  taxRate: number;
  taxIncluded: boolean;
  currencyCode: string;
  currencySymbol: string;
  welcomeMessage?: string;
}

// Types de notification
export interface Notification {
  id: number;
  title: string;
  message: string;
  type: NotificationType;
  typeDisplayName: string;
  read: boolean;
  link?: string;
  relatedId?: number;
  createdAt: string;
}

export enum NotificationType {
  NEW_ORDER = 'NEW_ORDER',
  ORDER_CANCELLED = 'ORDER_CANCELLED',
  ORDER_CONFIRMED = 'ORDER_CONFIRMED',
  ORDER_SHIPPED = 'ORDER_SHIPPED',
  ORDER_DELIVERED = 'ORDER_DELIVERED',
  NEW_REVIEW = 'NEW_REVIEW',
  NEW_COMMENT = 'NEW_COMMENT',
  LOW_STOCK = 'LOW_STOCK',
  OUT_OF_STOCK = 'OUT_OF_STOCK',
  NEW_USER = 'NEW_USER',
  PAYMENT_RECEIVED = 'PAYMENT_RECEIVED',
  SYSTEM = 'SYSTEM'
}

// Types de dashboard
export interface DashboardStats {
  todaySales: number;
  monthlySales: number;
  totalRevenue: number;
  todayOrders: number;
  monthlyOrders: number;
  totalOrders: number;
  totalCustomers: number;
  totalProducts: number;
  lowStockProducts: number;
  outOfStockProducts: number;
  pendingOrders: number;
  recentOrders: Order[];
  popularProducts: Product[];
  recentNotifications: Notification[];
  salesChart: { date: string; sales: number }[];
  ordersChart: { date: string; orders: number }[];
  ratingDistribution: { [key: number]: number };
}

// Types génériques
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
  hasNext: boolean;
  hasPrevious: boolean;
}

export interface SearchFilters {
  query?: string;
  categoryId?: number;
  subcategoryId?: number;
  minPrice?: number;
  maxPrice?: number;
  brand?: string;
  colors?: string[];
  sizes?: string[];
  inStock?: boolean;
  featured?: boolean;
  onSale?: boolean;
  newArrival?: boolean;
  sortBy?: string;
  sortOrder?: 'asc' | 'desc';
  page?: number;
  size?: number;
}
