import { useQuery } from '@tanstack/react-query';
import { favoriteService } from '@/services/productService';
import { useAuthStore } from '@/context/authStore';
import ProductCard from '@/components/ProductCard';
import { Heart } from 'lucide-react';
import { Link } from 'react-router-dom';

const FavoritesPage = () => {
  const { user } = useAuthStore();

  const { data: favorites, isLoading } = useQuery({
    queryKey: ['favorites', user?.id],
    queryFn: () => favoriteService.getUserFavoritesList(user!.id),
    enabled: !!user?.id,
  });

  if (!user) return null;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Mes Favoris</h1>

      {isLoading ? (
        <div className="flex justify-center py-12">
          <span className="loading loading-spinner loading-lg text-primary"></span>
        </div>
      ) : favorites?.data && favorites.data.length > 0 ? (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {favorites.data.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <Heart className="w-16 h-16 text-base-300 mx-auto mb-4" />
          <p className="text-lg text-base-content/60 mb-4">
            Vous n'avez pas encore de favoris
          </p>
          <Link to="/" className="btn btn-primary">
            Découvrir des produits
          </Link>
        </div>
      )}
    </div>
  );
};

export default FavoritesPage;
