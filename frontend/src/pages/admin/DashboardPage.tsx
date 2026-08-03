import { useQuery } from '@tanstack/react-query';
import { apiRequest } from '@/services/api';
import type { ApiResponse, DashboardStats } from '@/types';
import { DollarSign, ShoppingCart, Users, Package, TrendingUp, Clock } from 'lucide-react';
import { Link } from 'react-router-dom';

const DashboardPage = () => {
  const { data: stats, isLoading } = useQuery({
    queryKey: ['dashboard'],
    queryFn: () => apiRequest.get<ApiResponse<DashboardStats>>('/admin/dashboard'),
  });

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <span className="loading loading-spinner loading-lg text-primary"></span>
      </div>
    );
  }

  const s = stats?.data;

  const statCards = [
    {
      title: 'Ventes du jour',
      value: `${(s?.todaySales || 0).toLocaleString()} CFA`,
      icon: DollarSign,
      color: 'text-success',
      bg: 'bg-success/10',
    },
    {
      title: 'Ventes du mois',
      value: `${(s?.monthlySales || 0).toLocaleString()} CFA`,
      icon: TrendingUp,
      color: 'text-primary',
      bg: 'bg-primary/10',
    },
    {
      title: 'Commandes en attente',
      value: s?.pendingOrders || 0,
      icon: Clock,
      color: 'text-warning',
      bg: 'bg-warning/10',
    },
    {
      title: 'Total clients',
      value: s?.totalCustomers || 0,
      icon: Users,
      color: 'text-info',
      bg: 'bg-info/10',
    },
    {
      title: 'Total produits',
      value: s?.totalProducts || 0,
      icon: Package,
      color: 'text-secondary',
      bg: 'bg-secondary/10',
    },
    {
      title: 'Rupture de stock',
      value: s?.outOfStockProducts || 0,
      icon: ShoppingCart,
      color: 'text-error',
      bg: 'bg-error/10',
    },
  ];

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">Tableau de bord</h1>

      {/* Stats Grid */}
      <div className="grid grid-cols-2 lg:grid-cols-3 xl:grid-cols-6 gap-4">
        {statCards.map((stat, index) => (
          <div key={index} className="card bg-base-100 card-bordered">
            <div className="card-body p-4">
              <div className={`w-10 h-10 ${stat.bg} rounded-lg flex items-center justify-center mb-3`}>
                <stat.icon className={`w-5 h-5 ${stat.color}`} />
              </div>
              <p className="text-sm text-base-content/60">{stat.title}</p>
              <p className={`text-2xl font-bold ${stat.color}`}>{stat.value}</p>
            </div>
          </div>
        ))}
      </div>

      <div className="grid lg:grid-cols-2 gap-6">
        {/* Recent Orders */}
        <div className="card bg-base-100 card-bordered">
          <div className="card-body">
            <div className="flex items-center justify-between mb-4">
              <h2 className="card-title">Commandes récentes</h2>
              <Link to="/admin/orders" className="btn btn-ghost btn-sm">Voir tout</Link>
            </div>
            <div className="overflow-x-auto">
              <table className="table table-sm">
                <thead>
                  <tr>
                    <th>N°</th>
                    <th>Client</th>
                    <th>Total</th>
                    <th>Statut</th>
                  </tr>
                </thead>
                <tbody>
                  {s?.recentOrders?.map((order) => (
                    <tr key={order.id}>
                      <td className="font-mono">{order.orderNumber}</td>
                      <td>{order.customerName}</td>
                      <td>{order.total.toLocaleString()} CFA</td>
                      <td>
                        <span className="badge badge-sm">{order.statusDisplayName}</span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        {/* Popular Products */}
        <div className="card bg-base-100 card-bordered">
          <div className="card-body">
            <div className="flex items-center justify-between mb-4">
              <h2 className="card-title">Produits populaires</h2>
              <Link to="/admin/products" className="btn btn-ghost btn-sm">Voir tout</Link>
            </div>
            <div className="space-y-3">
              {s?.popularProducts?.map((product) => (
                <div key={product.id} className="flex items-center gap-3">
                  <div className="w-12 h-12 bg-base-200 rounded-lg overflow-hidden">
                    {product.mainImage && (
                      <img src={product.mainImage} alt="" className="w-full h-full object-cover" />
                    )}
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="font-medium truncate">{product.name}</p>
                    <p className="text-sm text-base-content/60">{product.soldCount} vendus</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* Sales Chart */}
      <div className="card bg-base-100 card-bordered">
        <div className="card-body">
          <h2 className="card-title mb-4">Ventes des 7 derniers jours</h2>
          <div className="h-64 flex items-end justify-around gap-2">
            {s?.salesChart?.map((day, index) => (
              <div key={index} className="flex flex-col items-center gap-2">
                <div
                  className="w-12 bg-primary rounded-t-lg transition-all"
                  style={{ height: `${Math.max(20, (day.sales / Math.max(...(s?.salesChart?.map(d => d.sales) || [1]))) * 200)}px` }}
                />
                <span className="text-xs text-base-content/60">
                  {new Date(day.date).toLocaleDateString('fr-FR', { weekday: 'short' })}
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
