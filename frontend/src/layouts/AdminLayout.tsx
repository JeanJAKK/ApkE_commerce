import { Outlet, Link, useLocation } from 'react-router-dom';
import { useAuthStore } from '@/context/authStore';
import {
  LayoutDashboard,
  Package,
  FolderTree,
  ShoppingCart,
  Users,
  Star,
  Tag,
  Settings,
  LogOut,
  Menu,
  X,
} from 'lucide-react';
import { useState } from 'react';

const AdminLayout = () => {
  const location = useLocation();
  const { logout, user } = useAuthStore();
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const menuItems = [
    { path: '/admin/dashboard', icon: LayoutDashboard, label: 'Tableau de bord' },
    { path: '/admin/products', icon: Package, label: 'Produits' },
    { path: '/admin/categories', icon: FolderTree, label: 'Catégories' },
    { path: '/admin/orders', icon: ShoppingCart, label: 'Commandes' },
    { path: '/admin/users', icon: Users, label: 'Utilisateurs' },
    { path: '/admin/reviews', icon: Star, label: 'Avis' },
    { path: '/admin/promotions', icon: Tag, label: 'Promotions' },
    { path: '/admin/settings', icon: Settings, label: 'Paramètres' },
  ];

  const isActive = (path: string) => location.pathname === path;

  return (
    <div className="min-h-screen bg-base-200">
      {/* Mobile header */}
      <div className="lg:hidden fixed top-0 left-0 right-0 z-50 bg-base-100 border-b p-4">
        <div className="flex items-center justify-between">
          <Link to="/admin/dashboard" className="text-xl font-bold text-primary">
            Admin
          </Link>
          <button onClick={() => setIsSidebarOpen(!isSidebarOpen)}>
            {isSidebarOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>
      </div>

      {/* Sidebar */}
      <aside
        className={`fixed top-0 left-0 z-40 h-screen w-64 bg-base-100 border-r transform transition-transform lg:translate-x-0 ${
          isSidebarOpen ? 'translate-x-0' : '-translate-x-full'
        }`}
      >
        <div className="flex flex-col h-full">
          <div className="p-6 border-b">
            <Link to="/admin/dashboard" className="text-2xl font-bold text-primary">
              Ma Boutique
            </Link>
            <p className="text-sm text-base-content/60 mt-1">Administration</p>
          </div>

          <nav className="flex-1 overflow-y-auto p-4">
            <ul className="space-y-1">
              {menuItems.map((item) => (
                <li key={item.path}>
                  <Link
                    to={item.path}
                    className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-colors ${
                      isActive(item.path)
                        ? 'bg-primary text-white'
                        : 'hover:bg-base-200'
                    }`}
                    onClick={() => setIsSidebarOpen(false)}
                  >
                    <item.icon className="w-5 h-5" />
                    <span>{item.label}</span>
                  </Link>
                </li>
              ))}
            </ul>
          </nav>

          <div className="p-4 border-t">
            <Link
              to="/"
              className="flex items-center gap-3 px-4 py-3 rounded-lg hover:bg-base-200 mb-2"
            >
              <LayoutDashboard className="w-5 h-5" />
              <span>Voir le site</span>
            </Link>
            <button
              onClick={logout}
              className="flex items-center gap-3 px-4 py-3 rounded-lg hover:bg-base-200 w-full text-left text-error"
            >
              <LogOut className="w-5 h-5" />
              <span>Déconnexion</span>
            </button>
          </div>
        </div>
      </aside>

      {/* Overlay */}
      {isSidebarOpen && (
        <div
          className="fixed inset-0 bg-black/50 z-30 lg:hidden"
          onClick={() => setIsSidebarOpen(false)}
        />
      )}

      {/* Main content */}
      <main className="lg:ml-64 p-4 pt-20 lg:pt-4">
        <Outlet />
      </main>
    </div>
  );
};

export default AdminLayout;
