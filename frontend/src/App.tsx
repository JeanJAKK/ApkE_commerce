import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuthStore } from '@/context/authStore';
import Layout from '@/layouts/Layout';
import PublicLayout from '@/layouts/PublicLayout';

// Pages publiques
import HomePage from '@/pages/HomePage';
import ProductPage from '@/pages/ProductPage';
import CategoryPage from '@/pages/CategoryPage';
import SearchPage from '@/pages/SearchPage';
import CartPage from '@/pages/CartPage';
import CheckoutPage from '@/pages/CheckoutPage';
import OrderConfirmationPage from '@/pages/OrderConfirmationPage';
import LoginPage from '@/pages/LoginPage';
import RegisterPage from '@/pages/RegisterPage';
import ProfilePage from '@/pages/ProfilePage';
import OrdersPage from '@/pages/OrdersPage';
import OrderDetailPage from '@/pages/OrderDetailPage';
import FavoritesPage from '@/pages/FavoritesPage';

// Pages admin
import AdminLayout from '@/layouts/AdminLayout';
import DashboardPage from '@/pages/admin/DashboardPage';
import ProductsAdminPage from '@/pages/admin/ProductsAdminPage';
import ProductFormPage from '@/pages/admin/ProductFormPage';
import CategoriesAdminPage from '@/pages/admin/CategoriesAdminPage';
import OrdersAdminPage from '@/pages/admin/OrdersAdminPage';
import OrderAdminDetailPage from '@/pages/admin/OrderAdminDetailPage';
import UsersAdminPage from '@/pages/admin/UsersAdminPage';
import ReviewsAdminPage from '@/pages/admin/ReviewsAdminPage';
import PromotionsAdminPage from '@/pages/admin/PromotionsAdminPage';
import SettingsPage from '@/pages/admin/SettingsPage';

import NotFoundPage from '@/pages/NotFoundPage';

// Composant de protection des routes
const ProtectedRoute = ({ children, requireAdmin = false }: { children: React.ReactNode; requireAdmin?: boolean }) => {
  const { isAuthenticated, isAdmin, isLoading } = useAuthStore();

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <span className="loading loading-spinner loading-lg"></span>
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (requireAdmin && !isAdmin) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};

function App() {
  const { isAuthenticated, isAdmin } = useAuthStore();

  return (
    <Routes>
      {/* Routes publiques */}
      <Route element={<Layout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/product/:slug" element={<ProductPage />} />
        <Route path="/category/:slug" element={<CategoryPage />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/checkout" element={<CheckoutPage />} />
        <Route path="/order-confirmation/:orderNumber" element={<OrderConfirmationPage />} />
        <Route path="/favorites" element={
          <ProtectedRoute>
            <FavoritesPage />
          </ProtectedRoute>
        } />
        <Route path="/profile" element={
          <ProtectedRoute>
            <ProfilePage />
          </ProtectedRoute>
        } />
        <Route path="/orders" element={
          <ProtectedRoute>
            <OrdersPage />
          </ProtectedRoute>
        } />
        <Route path="/orders/:orderNumber" element={
          <ProtectedRoute>
            <OrderDetailPage />
          </ProtectedRoute>
        } />
        <Route path="/login" element={isAuthenticated ? <Navigate to="/" /> : <LoginPage />} />
        <Route path="/register" element={isAuthenticated ? <Navigate to="/" /> : <RegisterPage />} />
      </Route>

      {/* Routes Admin */}
      <Route path="/admin" element={
        <ProtectedRoute requireAdmin>
          <AdminLayout />
        </ProtectedRoute>
      }>
        <Route index element={<Navigate to="/admin/dashboard" />} />
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="products" element={<ProductsAdminPage />} />
        <Route path="products/new" element={<ProductFormPage />} />
        <Route path="products/:id/edit" element={<ProductFormPage />} />
        <Route path="categories" element={<CategoriesAdminPage />} />
        <Route path="orders" element={<OrdersAdminPage />} />
        <Route path="orders/:id" element={<OrderAdminDetailPage />} />
        <Route path="users" element={<UsersAdminPage />} />
        <Route path="reviews" element={<ReviewsAdminPage />} />
        <Route path="promotions" element={<PromotionsAdminPage />} />
        <Route path="settings" element={<SettingsPage />} />
      </Route>

      {/* Route 404 */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}

export default App;
