import { Link } from 'react-router-dom';
import { Heart, ShoppingCart, Star } from 'lucide-react';
import { useCartStore } from '@/context/cartStore';
import { useAuthStore } from '@/context/authStore';
import type { Product } from '@/types';

interface ProductCardProps {
  product: Product;
}

const ProductCard = ({ product }: ProductCardProps) => {
  const { addItem, openCart } = useCartStore();
  const { isAuthenticated } = useAuthStore();

  const handleAddToCart = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();

    if (!product.inStock) return;

    addItem({
      id: Date.now(),
      productId: product.id,
      productName: product.name,
      productImage: product.mainImage || product.images?.[0],
      unitPrice: product.price,
      discountedPrice: product.discountedPrice,
      quantity: 1,
      totalPrice: product.discountedPrice,
      availableStock: product.stock,
      inStock: product.inStock,
      addedAt: new Date().toISOString(),
    });

    openCart();
  };

  const handleAddToFavorites = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    // TODO: Implémenter l'ajout aux favoris
    console.log('Ajouter aux favoris:', product.id);
  };

  return (
    <Link
      to={`/product/${product.slug}`}
      className="card card-bordered bg-base-100 card-hover group"
    >
      {/* Image */}
      <figure className="relative aspect-square bg-base-200 overflow-hidden">
        {product.mainImage || product.images?.[0] ? (
          <img
            src={product.mainImage || product.images[0]}
            alt={product.name}
            className="w-full h-full object-cover product-image-zoom"
          />
        ) : (
          <div className="w-full h-full flex items-center justify-center text-6xl">
            📦
          </div>
        )}

        {/* Badges */}
        <div className="absolute top-2 left-2 flex flex-col gap-2">
          {product.onSale && product.discountPercent && (
            <span className="badge-sale">-{product.discountPercent}%</span>
          )}
          {product.newArrival && (
            <span className="badge-new">Nouveau</span>
          )}
          {product.featured && !product.onSale && (
            <span className="badge badge-secondary text-white">★ Vedette</span>
          )}
        </div>

        {/* Action buttons */}
        <div className="absolute top-2 right-2 flex flex-col gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
          <button
            className="btn btn-circle btn-sm bg-base-100/90 hover:bg-base-100"
            onClick={handleAddToFavorites}
          >
            <Heart className="w-4 h-4" />
          </button>
        </div>

        {/* Out of stock overlay */}
        {!product.inStock && (
          <div className="absolute inset-0 bg-black/50 flex items-center justify-center">
            <span className="badge badge-error badge-lg">Rupture de stock</span>
          </div>
        )}
      </figure>

      {/* Content */}
      <div className="card-body p-4">
        {/* Brand */}
        {product.brand && (
          <p className="text-xs text-base-content/60 uppercase tracking-wider">
            {product.brand}
          </p>
        )}

        {/* Name */}
        <h3 className="font-semibold line-clamp-2 min-h-[3rem]">
          {product.name}
        </h3>

        {/* Rating */}
        <div className="flex items-center gap-2">
          <div className="flex gap-0.5">
            {[1, 2, 3, 4, 5].map((star) => (
              <Star
                key={star}
                className={`w-4 h-4 ${
                  star <= Math.round(product.averageRating)
                    ? 'text-warning fill-warning'
                    : 'text-base-300'
                }`}
              />
            ))}
          </div>
          <span className="text-sm text-base-content/60">
            ({product.reviewCount})
          </span>
        </div>

        {/* Price */}
        <div className="mt-2">
          <div className="flex items-center gap-2">
            <span className="text-xl font-bold text-primary">
              {product.discountedPrice.toLocaleString()} CFA
            </span>
            {product.oldPrice && product.oldPrice > product.discountedPrice && (
              <span className="text-sm text-base-content/50 line-through">
                {product.oldPrice.toLocaleString()} CFA
              </span>
            )}
          </div>
        </div>

        {/* Add to cart button */}
        <button
          className="btn btn-primary btn-sm w-full mt-3"
          onClick={handleAddToCart}
          disabled={!product.inStock}
        >
          <ShoppingCart className="w-4 h-4 mr-2" />
          {product.inStock ? 'Ajouter au panier' : 'Indisponible'}
        </button>
      </div>
    </Link>
  );
};

export default ProductCard;
