import { Link } from 'react-router-dom';
import { Minus, Plus, Trash2, ShoppingBag, ArrowLeft } from 'lucide-react';
import { useCartStore } from '@/context/cartStore';

const CartPage = () => {
  const { items, removeItem, updateQuantity, clearCart, getTotalPrice } = useCartStore();
  const subtotal = getTotalPrice();
  const shipping = subtotal >= 50000 ? 0 : 2500;
  const total = subtotal + shipping;

  if (items.length === 0) {
    return (
      <div className="container mx-auto px-4 py-16 text-center">
        <ShoppingBag className="w-24 h-24 text-base-300 mx-auto mb-6" />
        <h1 className="text-3xl font-bold mb-4">Votre panier est vide</h1>
        <p className="text-base-content/60 mb-8">
          Découvrez nos produits et ajoutez-les à votre panier
        </p>
        <Link to="/" className="btn btn-primary btn-lg">
          Continuer mes achats
        </Link>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Mon Panier</h1>

      <div className="grid lg:grid-cols-3 gap-8">
        {/* Cart Items */}
        <div className="lg:col-span-2 space-y-4">
          {items.map((item) => (
            <div
              key={`${item.productId}-${item.selectedColor}-${item.selectedSize}`}
              className="card bg-base-100 card-bordered p-4"
            >
              <div className="flex gap-4">
                <div className="w-24 h-24 bg-base-200 rounded-lg overflow-hidden flex-shrink-0">
                  {item.productImage ? (
                    <img
                      src={item.productImage}
                      alt={item.productName}
                      className="w-full h-full object-cover"
                    />
                  ) : (
                    <div className="w-full h-full flex items-center justify-center">📦</div>
                  )}
                </div>

                <div className="flex-1">
                  <h3 className="font-semibold">{item.productName}</h3>
                  {item.selectedColor && (
                    <p className="text-sm text-base-content/60">Couleur: {item.selectedColor}</p>
                  )}
                  {item.selectedSize && (
                    <p className="text-sm text-base-content/60">Taille: {item.selectedSize}</p>
                  )}
                  <p className="font-bold text-primary mt-2">
                    {item.discountedPrice.toLocaleString()} CFA
                  </p>
                </div>

                <div className="flex flex-col items-end justify-between">
                  <button
                    className="btn btn-ghost btn-sm btn-circle text-error"
                    onClick={() => removeItem(item.id)}
                  >
                    <Trash2 className="w-4 h-4" />
                  </button>

                  <div className="join">
                    <button
                      className="join-item btn btn-sm"
                      onClick={() => updateQuantity(item.id, item.quantity - 1)}
                    >
                      <Minus className="w-4 h-4" />
                    </button>
                    <span className="join-item btn btn-sm w-12">{item.quantity}</span>
                    <button
                      className="join-item btn btn-sm"
                      onClick={() => updateQuantity(item.id, item.quantity + 1)}
                      disabled={item.quantity >= item.availableStock}
                    >
                      <Plus className="w-4 h-4" />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}

          <button className="btn btn-ghost text-error" onClick={clearCart}>
            Vider le panier
          </button>
        </div>

        {/* Summary */}
        <div className="lg:col-span-1">
          <div className="card bg-base-100 card-bordered p-6 sticky top-24">
            <h3 className="font-semibold text-lg mb-4">Récapitulatif</h3>

            <div className="space-y-3 mb-6">
              <div className="flex justify-between">
                <span>Sous-total</span>
                <span>{subtotal.toLocaleString()} CFA</span>
              </div>
              <div className="flex justify-between">
                <span>Livraison</span>
                <span>{shipping === 0 ? 'Gratuite' : `${shipping.toLocaleString()} CFA`}</span>
              </div>
              <hr />
              <div className="flex justify-between font-bold text-lg">
                <span>Total</span>
                <span className="text-primary">{total.toLocaleString()} CFA</span>
              </div>
            </div>

            {shipping > 0 && (
              <p className="text-sm text-base-content/60 mb-4">
                🎉 Plus que {(50000 - subtotal).toLocaleString()} CFA pour la livraison gratuite!
              </p>
            )}

            <Link to="/checkout" className="btn btn-primary w-full btn-lg">
              Passer la commande
            </Link>

            <Link to="/" className="btn btn-ghost w-full mt-2">
              <ArrowLeft className="w-4 h-4 mr-2" />
              Continuer mes achats
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CartPage;
