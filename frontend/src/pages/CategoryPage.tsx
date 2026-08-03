import { useParams, Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { categoryService, productService } from '@/services/productService';
import ProductCard from '@/components/ProductCard';

const CategoryPage = () => {
  const { slug } = useParams<{ slug: string }>();

  const { data: category, isLoading: categoryLoading } = useQuery({
    queryKey: ['category', slug],
    queryFn: () => categoryService.getBySlug(slug!),
    enabled: !!slug,
  });

  const { data: products, isLoading: productsLoading } = useQuery({
    queryKey: ['products', 'category', category?.data?.id],
    queryFn: () => productService.getByCategory(category!.data.id, 20),
    enabled: !!category?.data?.id,
  });

  const { data: subcategories } = useQuery({
    queryKey: ['category', category?.data?.id, 'subcategories'],
    queryFn: () => categoryService.getSubcategories(category!.data.id),
    enabled: !!category?.data?.id,
  });

  if (categoryLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <span className="loading loading-spinner loading-lg text-primary"></span>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Hero */}
      <div className="bg-gradient-to-r from-primary/10 to-secondary/10 rounded-3xl p-8 mb-8">
        <h1 className="text-4xl font-display font-bold mb-4">{category?.data?.name}</h1>
        {category?.data?.description && (
          <p className="text-lg text-base-content/70">{category.data.description}</p>
        )}
      </div>

      {/* Subcategories */}
      {subcategories?.data && subcategories.data.length > 0 && (
        <div className="mb-8">
          <h2 className="text-xl font-semibold mb-4">Sous-catégories</h2>
          <div className="flex flex-wrap gap-3">
            {subcategories.data.map((sub) => (
              <Link
                key={sub.id}
                to={`/category/${sub.slug}`}
                className="btn btn-outline btn-sm"
              >
                {sub.name}
              </Link>
            ))}
          </div>
        </div>
      )}

      {/* Products */}
      {productsLoading ? (
        <div className="flex justify-center py-12">
          <span className="loading loading-spinner loading-lg text-primary"></span>
        </div>
      ) : products?.data && products.data.length > 0 ? (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {products.data.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <p className="text-lg text-base-content/60 mb-4">
            Aucun produit dans cette catégorie
          </p>
          <Link to="/" className="btn btn-primary">
            Retour à l'accueil
          </Link>
        </div>
      )}
    </div>
  );
};

export default CategoryPage;
