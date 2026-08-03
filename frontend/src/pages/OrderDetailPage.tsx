import { useParams, Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { orderService } from '@/services/orderService';
import { ArrowLeft, Package, Truck, CheckCircle, Clock } from 'lucide-react';

const OrderDetailPage = () => {
  const { orderNumber } = useParams<{ orderNumber: string }>();

  const { data: order, isLoading } = useQuery({
    queryKey: ['order', orderNumber],
    queryFn: () => orderService.getByOrderNumber(orderNumber!),
    enabled: !!orderNumber,
  });

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'PENDING': return <Clock className="w-8 h-8 text-warning" />;
      case 'CONFIRMED': return <CheckCircle className="w-8 h-8 text-info" />;
      case 'PREPARING': return <Package className="w-8 h-8 text-secondary" />;
      case 'SHIPPED': return <Truck className="w-8 h-8 text-primary" />;
      case 'DELIVERED': return <CheckCircle className="w-8 h-8 text-success" />;
      default: return <Clock className="w-8 h-8" />;
    }
  };

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <span className="loading loading-spinner loading-lg text-primary"></span>
      </div>
    );
  }

  if (!order?.data) {
    return (
      <div className="container mx-auto px-4 py-16 text-center">
        <h1 className="text-2xl font-bold mb-4">Commande non trouvée</h1>
        <Link to="/orders" className="btn btn-primary">Mes commandes</Link>
      </div>
    );
  }

  const o = order.data;

  return (
    <div className="container mx-auto px-4 py-8">
      <Link to="/orders" className="btn btn-ghost mb-6">
        <ArrowLeft className="w-4 h-4 mr-2" />
        Retour aux commandes
      </Link>

      <div className="flex items-center justify-between mb-8">
        <div>
          <h1 className="text-3xl font-bold">{o.orderNumber}</h1>
          <p className="text-base-content/60">
            Commandé le {new Date(o.createdAt).toLocaleDateString('fr-FR', {
              year: 'numeric',
              month: 'long',
              day: 'numeric',
            })}
          </p>
        </div>
        <div className="flex items-center gap-2">
          {getStatusIcon(o.status)}
          <span className="font-semibold">{o.statusDisplayName}</span>
        </div>
      </div>

      <div className="grid lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 space-y-6">
          <div className="card bg-base-100 card-bordered">
            <div className="card-body">
              <h2 className="card-title">Articles commandés</h2>
              <div className="space-y-4">
                {o.items.map((item, index) => (
                  <div key={index} className="flex gap-4">
                    <div className="w-20 h-20 bg-base-200 rounded-lg overflow-hidden">
                      {item.productImage && (
                        <img src={item.productImage} alt="" className="w-full h-full object-cover" />
                      )}
                    </div>
                    <div className="flex-1">
                      <p className="font-medium">{item.productName}</p>
                      {item.selectedColor && <p className="text-sm text-base-content/60">Couleur: {item.selectedColor}</p>}
                      {item.selectedSize && <p className="text-sm text-base-content/60">Taille: {item.selectedSize}</p>}
                      <p className="text-sm">Quantité: {item.quantity}</p>
                    </div>
                    <p className="font-semibold">{item.totalPrice.toLocaleString()} CFA</p>
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="card bg-base-100 card-bordered p-6">
            <h2 className="font-semibold mb-4">Adresse de livraison</h2>
            <p>{o.shippingAddress}</p>
            <p>{o.shippingCity}, {o.shippingCountry}</p>
          </div>
        </div>

        <div>
          <div className="card bg-base-100 card-bordered p-6 sticky top-24">
            <h2 className="font-semibold text-lg mb-4">Résumé</h2>
            <div className="space-y-2">
              <div className="flex justify-between">
                <span>Sous-total</span>
                <span>{o.subtotal.toLocaleString()} CFA</span>
              </div>
              <div className="flex justify-between">
                <span>Livraison</span>
                <span>{o.shippingCost === 0 ? 'Gratuite' : `${o.shippingCost.toLocaleString()} CFA`}</span>
              </div>
              {o.discount > 0 && (
                <div className="flex justify-between text-success">
                  <span>Réduction</span>
                  <span>-{o.discount.toLocaleString()} CFA</span>
                </div>
              )}
              <hr />
              <div className="flex justify-between font-bold text-xl">
                <span>Total</span>
                <span className="text-primary">{o.total.toLocaleString()} CFA</span>
              </div>
            </div>

            <div className="mt-6">
              <p className="text-sm text-base-content/60 mb-2">Mode de paiement</p>
              <p className="font-medium">{o.paymentMethodDisplayName}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrderDetailPage;
