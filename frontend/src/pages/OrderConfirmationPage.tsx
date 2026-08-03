import { useParams, Link } from 'react-router-dom';
import { CheckCircle, Package, Truck } from 'lucide-react';

const OrderConfirmationPage = () => {
  const { orderNumber } = useParams<{ orderNumber: string }>();

  return (
    <div className="container mx-auto px-4 py-16 text-center">
      <div className="max-w-2xl mx-auto">
        <div className="bg-success/10 w-24 h-24 rounded-full flex items-center justify-center mx-auto mb-6">
          <CheckCircle className="w-12 h-12 text-success" />
        </div>

        <h1 className="text-3xl font-bold mb-4">Commande confirmée!</h1>
        <p className="text-lg text-base-content/70 mb-6">
          Merci pour votre commande. Vous recevrez bientôt un email de confirmation.
        </p>

        <div className="card bg-base-100 card-bordered p-6 mb-8">
          <h2 className="font-semibold mb-4">Numéro de commande</h2>
          <p className="text-2xl font-bold text-primary">{orderNumber}</p>
        </div>

        <div className="grid md:grid-cols-2 gap-4 mb-8">
          <div className="card bg-base-200 p-4">
            <Package className="w-8 h-8 text-primary mx-auto mb-2" />
            <h3 className="font-semibold">Commande reçue</h3>
            <p className="text-sm text-base-content/60">Nous préparons votre commande</p>
          </div>
          <div className="card bg-base-200 p-4">
            <Truck className="w-8 h-8 text-secondary mx-auto mb-2" />
            <h3 className="font-semibold">Livraison</h3>
            <p className="text-sm text-base-content/60">Expédition sous 24-48h</p>
          </div>
        </div>

        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <Link to="/orders" className="btn btn-primary">
            Voir mes commandes
          </Link>
          <Link to="/" className="btn btn-outline">
            Continuer mes achats
          </Link>
        </div>
      </div>
    </div>
  );
};

export default OrderConfirmationPage;
