import { Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { orderService } from '@/services/orderService';
import { useAuthStore } from '@/context/authStore';
import { Package, Clock, CheckCircle, XCircle, Truck } from 'lucide-react';

const OrdersPage = () => {
  const { user } = useAuthStore();

  const { data: orders, isLoading } = useQuery({
    queryKey: ['orders', user?.id],
    queryFn: () => orderService.getByUser(user!.id),
    enabled: !!user?.id,
  });

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'PENDING': return <Clock className="w-5 h-5 text-warning" />;
      case 'CONFIRMED': return <CheckCircle className="w-5 h-5 text-info" />;
      case 'PREPARING': return <Package className="w-5 h-5 text-secondary" />;
      case 'SHIPPED': return <Truck className="w-5 h-5 text-primary" />;
      case 'DELIVERED': return <CheckCircle className="w-5 h-5 text-success" />;
      case 'CANCELLED': return <XCircle className="w-5 h-5 text-error" />;
      default: return <Clock className="w-5 h-5" />;
    }
  };

  if (!user) return null;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Mes Commandes</h1>

      {isLoading ? (
        <div className="flex justify-center py-12">
          <span className="loading loading-spinner loading-lg text-primary"></span>
        </div>
      ) : orders?.data?.content && orders.data.content.length > 0 ? (
        <div className="space-y-4">
          {orders.data.content.map((order) => (
            <Link
              key={order.id}
              to={`/orders/${order.orderNumber}`}
              className="card bg-base-100 card-bordered hover:shadow-lg transition-shadow"
            >
              <div className="card-body">
                <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
                  <div>
                    <p className="font-bold text-lg">{order.orderNumber}</p>
                    <p className="text-sm text-base-content/60">
                      {new Date(order.createdAt).toLocaleDateString('fr-FR')}
                    </p>
                  </div>
                  <div className="flex items-center gap-4">
                    <div className="text-right">
                      <p className="font-bold text-xl text-primary">
                        {order.total.toLocaleString()} CFA
                      </p>
                      <p className="text-sm text-base-content/60">
                        {order.itemCount} article{order.itemCount > 1 ? 's' : ''}
                      </p>
                    </div>
                    <div className="flex items-center gap-2 badge badge-lg">
                      {getStatusIcon(order.status)}
                      {order.statusDisplayName}
                    </div>
                  </div>
                </div>
              </div>
            </Link>
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <Package className="w-16 h-16 text-base-300 mx-auto mb-4" />
          <p className="text-lg text-base-content/60 mb-4">Vous n'avez pas encore de commande</p>
          <Link to="/" className="btn btn-primary">Commencer mes achats</Link>
        </div>
      )}
    </div>
  );
};

export default OrdersPage;
