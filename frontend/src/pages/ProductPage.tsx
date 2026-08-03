import { useParams, Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { Heart, ShoppingCart, Star, Minus, Plus, Truck, Shield, RotateCcw } from 'lucide-react';
import { productService } from '@/services/productService';
import { useCartStore } from '@/context/cartStore';
import ProductCard from '@/components/ProductCard';

const ProductPage = () => {
  const { slug } = useParams<{ slug: string }>();
  const { addItem, openCart } = useCartStore();
  const [selectedColor, setSelectedColor] = React.useState<string>('');
  const [selectedSize, setSelectedSize] = React.useState<string>('');
  const [quantity, setQuantity] = React.useState(1);
  const [selectedImage, setSelectedImage] = React.useState(0);

  const { data: product, isLoading } = useQuery({
    queryKey: ['product', slug],
    queryFn: () => productService.getBySlug(slug!),
    enabled: !!slug,
  });

  const { data: similarProducts } = useQuery({
    queryKey: ['product', product?.data?.id, 'similar'],
    queryFn: () => productService.getSimilar(product!.data.id, 4),
    enabled: !!product?.data?.id,
  });

  React.useEffect(() => {
    if (product?.data) {
      if (product.data.colors?.length && !selectedColor) {
        setSelectedColor(product.data.colors[0]);
      }
      if (product.data.sizes?.length && !selectedSize) {
        setSelectedSize(product.data.sizes[0]);
      }
    }
  }, [product]);

  const handleAddToCart = () => {
    if (!product?.data) return;

    addItem({
      id: Date.now(),
      productId: product.data.id,
      productName: product.data.name,
      productImage: product.data.images?.[0],
      unitPrice: product.data.price,
      discountedPrice: product.data.discountedPrice,
      quantity,
      totalPrice: product.data.discountedPrice * quantity,
      selectedColor,
      selectedSize,
      availableStock: product.data.stock,
      inStock: product.data.inStock,
      addedAt: new Date().toISOString(),
    });

    openCart();
  };

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <span className="loading loading-spinner loading-lg text-primary"></span>
      </div>
    );
  }

  if (!product?.data) {
    return (
      <div className="container mx-auto px-4 py-16 text-center">
        <h1 className="text-2xl font-bold mb-4">Produit non trouvé</h1>
        <Link to="/" className="btn btn-primary">Retour à l'accueil</Link>
      </div>
    );
  }

  const p = product.data;

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Breadcrumb */}
      <div className="text-sm breadcrumbs mb-6">
        <ul>
          <li><Link to="/">Accueil</Link></li>
          <li><Link to={`/category/${p.categoryName?.toLowerCase()}`}>{p.categoryName}</Link></li>
          <li>{p.name}</li>
        </ul>
      </div>

      <div className="grid lg:grid-cols-2 gap-12">
        {/* Images */}
        <div className="space-y-4">
          <div className="aspect-square bg-base-200 rounded-2xl overflow-hidden">
            {p.images?.[selectedImage] ? (
              <img
                src={p.images[selectedImage]}
                alt={p.name}
                className="w-full h-full object-cover"
              />
            ) : (
              <div className="w-full h-full flex items-center justify-center text-9xl">📦</div>
            )}
          </div>
          {p.images?.length > 1 && (
            <div className="flex gap-3 overflow-x-auto pb-2">
              {p.images.map((img, index) => (
                <button
                  key={index}
                  className={`w-20 h-20 rounded-lg overflow-hidden border-2 ${
                    selectedImage === index ? 'border-primary' : 'border-transparent'
                  }`}
                  onClick={() => setSelectedImage(index)}
                >
                  <img src={img} alt="" className="w-full h-full object-cover" />
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Details */}
        <div>
          {p.brand && (
            <p className="text-sm text-base-content/60 uppercase tracking-wider mb-2">
              {p.brand}
            </p>
          )}
          <h1 className="text-3xl font-bold mb-4">{p.name}</h1>

          {/* Rating */}
          <div className="flex items-center gap-3 mb-4">
            <div className="flex gap-1">
              {[1, 2, 3, 4, 5].map((star) => (
                <Star
                  key={star}
                  className={`w-5 h-5 ${
                    star <= Math.round(p.averageRating)
                      ? 'text-warning fill-warning'
                      : 'text-base-300'
                  }`}
                />
              ))}
            </div>
            <span className="text-base-content/60">
              {p.averageRating.toFixed(1)} ({p.reviewCount} avis)
            </span>
          </div>

          {/* Price */}
          <div className="flex items-baseline gap-4 mb-6">
            <span className="text-4xl font-bold text-primary">
              {p.discountedPrice.toLocaleString()} CFA
            </span>
            {p.oldPrice && p.oldPrice > p.discountedPrice && (
              <>
                <span className="text-xl text-base-content/50 line-through">
                  {p.oldPrice.toLocaleString()} CFA
                </span>
                <span className="badge badge-error">
                  -{p.discountPercent}%
                </span>
              </>
            )}
          </div>

          {/* Short description */}
          {p.shortDescription && (
            <p className="text-base-content/80 mb-6">{p.shortDescription}</p>
          )}

          {/* Colors */}
          {p.colors?.length > 0 && (
            <div className="mb-6">
              <p className="font-semibold mb-2">Couleur: {selectedColor}</p>
              <div className="flex gap-2">
                {p.colors.map((color) => (
                  <button
                    key={color}
                    className={`btn btn-sm ${
                      selectedColor === color ? 'btn-primary' : 'btn-ghost'
                    }`}
                    onClick={() => setSelectedColor(color)}
                  >
                    {color}
                  </button>
                ))}
              </div>
            </div>
          )}

          {/* Sizes */}
          {p.sizes?.length > 0 && (
            <div className="mb-6">
              <p className="font-semibold mb-2">Taille: {selectedSize}</p>
              <div className="flex gap-2">
                {p.sizes.map((size) => (
                  <button
                    key={size}
                    className={`btn btn-sm ${
                      selectedSize === size ? 'btn-primary' : 'btn-ghost'
                    }`}
                    onClick={() => setSelectedSize(size)}
                  >
                    {size}
                  </button>
                ))}
              </div>
            </div>
          )}

          {/* Stock */}
          <div className="mb-6">
            {p.inStock ? (
              <p className="text-success font-semibold">
                En stock ({p.stock} disponibles)
              </p>
            ) : (
              <p className="text-error font-semibold">Rupture de stock</p>
            )}
          </div>

          {/* Quantity */}
          <div className="flex items-center gap-4 mb-6">
            <span className="font-semibold">Quantité:</span>
            <div className="join">
              <button
                className="join-item btn"
                onClick={() => setQuantity(Math.max(1, quantity - 1))}
              >
                <Minus className="w-4 h-4" />
              </button>
              <input
                type="number"
                className="join-item input w-16 text-center"
                value={quantity}
                onChange={(e) => setQuantity(Math.max(1, parseInt(e.target.value) || 1))}
                min={1}
                max={p.stock}
              />
              <button
                className="join-item btn"
                onClick={() => setQuantity(Math.min(p.stock, quantity + 1))}
              >
                <Plus className="w-4 h-4" />
              </button>
            </div>
          </div>

          {/* Actions */}
          <div className="flex flex-col sm:flex-row gap-4 mb-8">
            <button
              className="btn btn-primary btn-lg flex-1"
              onClick={handleAddToCart}
              disabled={!p.inStock}
            >
              <ShoppingCart className="w-5 h-5 mr-2" />
              Ajouter au panier
            </button>
            <button className="btn btn-outline btn-lg">
              <Heart className="w-5 h-5" />
            </button>
          </div>

          {/* Features */}
          <div className="space-y-3">
            <div className="flex items-center gap-3">
              <Truck className="w-5 h-5 text-primary" />
              <span>Livraison gratuite dès 50 000 FCFA</span>
            </div>
            <div className="flex items-center gap-3">
              <Shield className="w-5 h-5 text-primary" />
              <span>Paiement 100% sécurisé</span>
            </div>
            <div className="flex items-center gap-3">
              <RotateCcw className="w-5 h-5 text-primary" />
              <span>Retours gratuits sous 14 jours</span>
            </div>
          </div>
        </div>
      </div>

      {/* Description */}
      <div className="mt-16">
        <h2 className="text-2xl font-bold mb-4">Description</h2>
        <div className="prose max-w-none">
          <p>{p.description}</p>
        </div>
      </div>

      {/* Similar Products */}
      {similarProducts?.data && similarProducts.data.length > 0 && (
        <div className="mt-16">
          <h2 className="text-2xl font-bold mb-6">Produits similaires</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            {similarProducts.data.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

import React from 'react';

export default ProductPage;
