import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCartStore } from '@/context/cartStore';
import { useAuthStore } from '@/context/authStore';
import { orderService } from '@/services/orderService';
import { CreditCard, Smartphone, Banknote } from 'lucide-react';
import toast from 'react-hot-toast';

const CheckoutPage = () => {
  const navigate = useNavigate();
  const { items, getTotalPrice, clearCart } = useCartStore();
  const { user } = useAuthStore();

  const [formData, setFormData] = useState({
    customerName: user?.fullName || '',
    customerEmail: user?.email || '',
    customerPhone: user?.phone || '',
    shippingAddress: user?.address || '',
    shippingCity: user?.city || '',
    shippingCountry: 'Togo',
    paymentMethod: 'CASH_ON_DELIVERY',
    promoCode: '',
    notes: '',
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  const subtotal = getTotalPrice();
  const shipping = subtotal >= 50000 ? 0 : 2500;
  const total = subtotal + shipping;

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (items.length === 0) {
      toast.error('Votre panier est vide');
      return;
    }

    setIsSubmitting(true);

    try {
      const response = await orderService.create({
        customerName: formData.customerName,
        customerEmail: formData.customerEmail,
        customerPhone: formData.customerPhone,
        shippingAddress: formData.shippingAddress,
        shippingCity: formData.shippingCity,
        shippingCountry: formData.shippingCountry,
        paymentMethod: formData.paymentMethod,
        items: items.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
          selectedColor: item.selectedColor,
          selectedSize: item.selectedSize,
        })),
        promoCode: formData.promoCode || undefined,
        notes: formData.notes || undefined,
      }, user?.id);

      clearCart();
      toast.success('Commande passée avec succès!');
      navigate(`/order-confirmation/${response.data.orderNumber}`);
    } catch (error) {
      toast.error('Erreur lors de la commande');
    } finally {
      setIsSubmitting(false);
    }
  };

  if (items.length === 0) {
    return (
      <div className="container mx-auto px-4 py-16 text-center">
        <h1 className="text-3xl font-bold mb-4">Votre panier est vide</h1>
        <p className="text-base-content/60 mb-8">Ajoutez des produits avant de passer commande</p>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Finaliser la commande</h1>

      <form onSubmit={handleSubmit} className="grid lg:grid-cols-2 gap-8">
        {/* Form */}
        <div className="space-y-6">
          <div className="card bg-base-100 card-bordered p-6">
            <h2 className="font-semibold text-lg mb-4">Informations de contact</h2>
            <div className="grid md:grid-cols-2 gap-4">
              <div className="form-control">
                <label className="label"><span className="label-text">Nom complet *</span></label>
                <input
                  type="text"
                  name="customerName"
                  className="input input-bordered"
                  value={formData.customerName}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">Email *</span></label>
                <input
                  type="email"
                  name="customerEmail"
                  className="input input-bordered"
                  value={formData.customerEmail}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="form-control md:col-span-2">
                <label className="label"><span className="label-text">Téléphone *</span></label>
                <input
                  type="tel"
                  name="customerPhone"
                  className="input input-bordered"
                  value={formData.customerPhone}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>
          </div>

          <div className="card bg-base-100 card-bordered p-6">
            <h2 className="font-semibold text-lg mb-4">Adresse de livraison</h2>
            <div className="space-y-4">
              <div className="form-control">
                <label className="label"><span className="label-text">Adresse *</span></label>
                <input
                  type="text"
                  name="shippingAddress"
                  className="input input-bordered"
                  placeholder="Rue, quartier..."
                  value={formData.shippingAddress}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="grid md:grid-cols-2 gap-4">
                <div className="form-control">
                  <label className="label"><span className="label-text">Ville *</span></label>
                  <input
                    type="text"
                    name="shippingCity"
                    className="input input-bordered"
                    value={formData.shippingCity}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-control">
                  <label className="label"><span className="label-text">Pays</span></label>
                  <input
                    type="text"
                    name="shippingCountry"
                    className="input input-bordered"
                    value={formData.shippingCountry}
                    onChange={handleChange}
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="card bg-base-100 card-bordered p-6">
            <h2 className="font-semibold text-lg mb-4">Mode de paiement</h2>
            <div className="space-y-3">
              <label className="flex items-center gap-3 p-4 border rounded-lg cursor-pointer hover:bg-base-200">
                <input
                  type="radio"
                  name="paymentMethod"
                  value="CASH_ON_DELIVERY"
                  checked={formData.paymentMethod === 'CASH_ON_DELIVERY'}
                  onChange={handleChange}
                  className="radio radio-primary"
                />
                <Banknote className="w-5 h-5" />
                <span>Paiement à la livraison</span>
              </label>
              <label className="flex items-center gap-3 p-4 border rounded-lg cursor-pointer hover:bg-base-200">
                <input
                  type="radio"
                  name="paymentMethod"
                  value="MOBILE_MONEY"
                  checked={formData.paymentMethod === 'MOBILE_MONEY'}
                  onChange={handleChange}
                  className="radio radio-primary"
                />
                <Smartphone className="w-5 h-5" />
                <span>Mobile Money (T-Money / Flooz)</span>
              </label>
              <label className="flex items-center gap-3 p-4 border rounded-lg cursor-pointer hover:bg-base-200">
                <input
                  type="radio"
                  name="paymentMethod"
                  value="CREDIT_CARD"
                  checked={formData.paymentMethod === 'CREDIT_CARD'}
                  onChange={handleChange}
                  className="radio radio-primary"
                />
                <CreditCard className="w-5 h-5" />
                <span>Carte bancaire</span>
              </label>
            </div>
          </div>

          <div className="form-control">
            <label className="label"><span className="label-text">Notes (optionnel)</span></label>
            <textarea
              name="notes"
              className="textarea textarea-bordered"
              rows={3}
              value={formData.notes}
              onChange={handleChange}
              placeholder="Instructions spéciales pour la livraison..."
            />
          </div>
        </div>

        {/* Order Summary */}
        <div>
          <div className="card bg-base-100 card-bordered p-6 sticky top-24">
            <h2 className="font-semibold text-lg mb-4">Récapitulatif</h2>

            <div className="space-y-3 max-h-64 overflow-y-auto mb-4">
              {items.map((item) => (
                <div key={`${item.productId}-${item.selectedColor}-${item.selectedSize}`} className="flex gap-3">
                  <div className="w-16 h-16 bg-base-200 rounded-lg overflow-hidden flex-shrink-0">
                    {item.productImage && (
                      <img src={item.productImage} alt="" className="w-full h-full object-cover" />
                    )}
                  </div>
                  <div className="flex-1">
                    <p className="text-sm font-medium line-clamp-1">{item.productName}</p>
                    <p className="text-sm text-base-content/60">
                      {item.quantity} x {item.discountedPrice.toLocaleString()} CFA
                    </p>
                  </div>
                  <p className="font-semibold">{item.totalPrice.toLocaleString()} CFA</p>
                </div>
              ))}
            </div>

            <div className="space-y-2 border-t pt-4">
              <div className="flex justify-between">
                <span>Sous-total</span>
                <span>{subtotal.toLocaleString()} CFA</span>
              </div>
              <div className="flex justify-between">
                <span>Livraison</span>
                <span>{shipping === 0 ? 'Gratuite' : `${shipping.toLocaleString()} CFA`}</span>
              </div>
              <hr />
              <div className="flex justify-between font-bold text-xl">
                <span>Total</span>
                <span className="text-primary">{total.toLocaleString()} CFA</span>
              </div>
            </div>

            <button
              type="submit"
              className="btn btn-primary w-full btn-lg mt-6"
              disabled={isSubmitting}
            >
              {isSubmitting ? (
                <span className="loading loading-spinner"></span>
              ) : (
                'Confirmer la commande'
              )}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default CheckoutPage;
