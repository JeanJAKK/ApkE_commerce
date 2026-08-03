import { Link } from 'react-router-dom';
import { X, Plus, Minus, Trash2, ShoppingBag } from 'lucide-react';
import { useCartStore } from '@/context/cartStore';

const CartDrawer = () => {
  const { items, isOpen, closeCart, removeItem, updateQuantity } = useCartStore();

  const subtotal = items.reduce((sum, item) => sum + item.totalPrice, 0);

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50">
      {/* Backdrop */}
      <div
        className="absolute inset-0 bg-black/50"
        onClick={closeCart}
      />

      {/* Drawer */}
      <div className="absolute right-0 top-0 h-full w-full max-w-md bg-base-100 shadow-xl animate-slide-in-right">
        <div className="flex flex-col h-full">
          {/* Header */}
          <div className="flex items-center justify-between p-4 border-b">
            <h2 className="text-xl font-semibold flex items-center gap-2">
              <ShoppingBag className="w-5 h-5" />
              Mon Panier
              <span className="badge badge-primary badge-sm">
                {items.length} {items.length === 1 ? 'article' : 'articles'}
              </span>
            </h2>
            <button
              className="btn btn-ghost btn-sm btn-circle"
              onClick={closeCart}
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          {/* Cart items */}
          <div className="flex-1 overflow-y-auto p-4">
            {items.length === 0 ? (
              <div className="flex flex-col items-center justify-center h-full text-center">
                <ShoppingBag className="w-16 h-16 text-base-300 mb-4" />
                <p className="text-lg font-medium mb-2">Votre panier est vide</p>
                <p className="text-base-content/60 mb-4">
                  Découvrez nos produits et ajoutez-les à votre panier
                </p>
                <Link
                  to="/search"
                  className="btn btn-primary"
                  onClick={closeCart}
                >
                  Continuer mes achats
                </Link>
              </div>
            ) : (
              <div className="space-y-4">
                {items.map((item) => (
                  <div
                    key={`${item.productId}-${item.selectedColor}-${item.selectedSize}`}
                    className="flex gap-4 p-3 bg-base-200 rounded-lg"
                  >
                    {/* Image */}
                    <div className="w-20 h-20 bg-base-300 rounded-lg overflow-hidden flex-shrink-0">
                      {item.productImage ? (
                        <img
                          src={item.productImage}
                          alt={item.productName}
                          className="w-full h-full object-cover"
                        />
                      ) : (
                        <div className="w-full h-full flex items-center justify-center">
                          <ShoppingBag className="w-8 h-8 text-base-content/30" />
                        </div>
                      )}
                    </div>

                    {/* Details */}
                    <div className="flex-1 min-w-0">
                      <h3 className="font-medium text-sm line-clamp-2">
                        {item.productName}
                      </h3>
                      {item.selectedColor && (
                        <p className="text-xs text-base-content/60 mt-1">
                          Couleur: {item.selectedColor}
                        </p>
                      )}
                      {item.selectedSize && (
                        <p className="text-xs text-base-content/60">
                          Taille: {item.selectedSize}
                        </p>
                      )}
                      <p className="font-semibold text-primary mt-1">
                        {item.discountedPrice.toLocaleString()} CFA
                        {item.discountedPrice < item.unitPrice && (
                          <span className="text-xs text-base-content/50 line-through ml-2">
                            {item.unitPrice.toLocaleString()} CFA
                          </span>
                        )}
                      </p>

                      {/* Quantity controls */}
                      <div className="flex items-center gap-2 mt-2">
                        <button
                          className="btn btn-ghost btn-xs btn-circle"
                          onClick={() => updateQuantity(item.id, item.quantity - 1)}
                        >
                          <Minus className="w-3 h-3" />
                        </button>
                        <span className="w-8 text-center text-sm">{item.quantity}</span>
                        <button
                          className="btn btn-ghost btn-xs btn-circle"
                          onClick={() => updateQuantity(item.id, item.quantity + 1)}
                          disabled={item.quantity >= item.availableStock}
                        >
                          <Plus className="w-3 h-3" />
                        </button>
                        <button
                          className="btn btn-ghost btn-xs btn-circle text-error ml-auto"
                          onClick={() => removeItem(item.id)}
                        >
                          <Trash2 className="w-3 h-3" />
                        </button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Footer */}
          {items.length > 0 && (
            <div className="p-4 border-t bg-base-100">
              <div className="flex justify-between items-center mb-4">
                <span className="font-semibold">Sous-total</span>
                <span className="text-xl font-bold text-primary">
                  {subtotal.toLocaleString()} CFA
                </span>
              </div>
              <p className="text-sm text-base-content/60 mb-4">
                Les frais de livraison seront calculés à l'étape suivante
              </p>
              <Link
                to="/checkout"
                className="btn btn-primary w-full"
                onClick={closeCart}
              >
                Passer la commande
              </Link>
              <Link
                to="/cart"
                className="btn btn-ghost w-full mt-2"
                onClick={closeCart}
              >
                Voir le panier
              </Link>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CartDrawer;
