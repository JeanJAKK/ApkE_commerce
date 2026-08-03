import { useSearchParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { productService, categoryService } from '@/services/productService';
import ProductCard from '@/components/ProductCard';
import { Filter, Grid, List, SlidersHorizontal } from 'lucide-react';
import { useState } from 'react';

const SearchPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [showFilters, setShowFilters] = useState(false);

  const query = searchParams.get('q') || '';
  const categoryId = searchParams.get('categoryId');
  const minPrice = searchParams.get('minPrice');
  const maxPrice = searchParams.get('maxPrice');
  const sortBy = searchParams.get('sortBy') || 'createdAt';
  const sortOrder = searchParams.get('sortOrder') || 'desc';

  const { data: products, isLoading } = useQuery({
    queryKey: ['search', query, categoryId, minPrice, maxPrice, sortBy, sortOrder],
    queryFn: () =>
      productService.search({
        query: query || undefined,
        categoryId: categoryId ? parseInt(categoryId) : undefined,
        minPrice: minPrice ? parseFloat(minPrice) : undefined,
        maxPrice: maxPrice ? parseFloat(maxPrice) : undefined,
        sortBy,
        sortOrder: sortOrder as 'asc' | 'desc',
        page: 0,
        size: 20,
      }),
  });

  const { data: categories } = useQuery({
    queryKey: ['categories'],
    queryFn: () => categoryService.getAll(),
  });

  const updateFilter = (key: string, value: string | null) => {
    const params = new URLSearchParams(searchParams);
    if (value) {
      params.set(key, value);
    } else {
      params.delete(key);
    }
    setSearchParams(params);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col lg:flex-row gap-8">
        {/* Filters Sidebar */}
        <div className={`lg:w-64 ${showFilters ? 'block' : 'hidden lg:block'}`}>
          <div className="card bg-base-100 card-bordered p-6 sticky top-24">
            <h3 className="font-semibold text-lg mb-4 flex items-center gap-2">
              <Filter className="w-5 h-5" />
              Filtres
            </h3>

            {/* Categories */}
            <div className="mb-6">
              <h4 className="font-medium mb-2">Catégories</h4>
              <div className="space-y-2">
                {categories?.data?.map((cat) => (
                  <label key={cat.id} className="flex items-center gap-2 cursor-pointer">
                    <input
                      type="radio"
                      name="category"
                      className="radio radio-sm radio-primary"
                      checked={categoryId === cat.id.toString()}
                      onChange={() => updateFilter('categoryId', cat.id.toString())}
                    />
                    <span className="text-sm">{cat.name}</span>
                  </label>
                ))}
                <label className="flex items-center gap-2 cursor-pointer">
                  <input
                    type="radio"
                    name="category"
                    className="radio radio-sm radio-primary"
                    checked={!categoryId}
                    onChange={() => updateFilter('categoryId', null)}
                  />
                  <span className="text-sm">Toutes</span>
                </label>
              </div>
            </div>

            {/* Price Range */}
            <div className="mb-6">
              <h4 className="font-medium mb-2">Prix</h4>
              <div className="space-y-2">
                <input
                  type="number"
                  placeholder="Min"
                  className="input input-bordered input-sm w-full"
                  value={minPrice || ''}
                  onChange={(e) => updateFilter('minPrice', e.target.value || null)}
                />
                <input
                  type="number"
                  placeholder="Max"
                  className="input input-bordered input-sm w-full"
                  value={maxPrice || ''}
                  onChange={(e) => updateFilter('maxPrice', e.target.value || null)}
                />
              </div>
            </div>

            {/* Sort */}
            <div>
              <h4 className="font-medium mb-2">Trier par</h4>
              <select
                className="select select-bordered select-sm w-full"
                value={`${sortBy}-${sortOrder}`}
                onChange={(e) => {
                  const [by, order] = e.target.value.split('-');
                  updateFilter('sortBy', by);
                  updateFilter('sortOrder', order);
                }}
              >
                <option value="createdAt-desc">Plus récents</option>
                <option value="price-asc">Prix croissant</option>
                <option value="price-desc">Prix décroissant</option>
                <option value="name-asc">Nom A-Z</option>
                <option value="soldCount-desc">Meilleures ventes</option>
              </select>
            </div>
          </div>
        </div>

        {/* Products Grid */}
        <div className="flex-1">
          {/* Header */}
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-2xl font-bold">
                {query ? `Résultats pour "${query}"` : 'Tous les produits'}
              </h1>
              <p className="text-base-content/60">
                {products?.data?.totalElements || 0} produits trouvés
              </p>
            </div>
            <button
              className="btn btn-ghost lg:hidden"
              onClick={() => setShowFilters(!showFilters)}
            >
              <SlidersHorizontal className="w-5 h-5" />
            </button>
          </div>

          {/* Products */}
          {isLoading ? (
            <div className="flex justify-center py-12">
              <span className="loading loading-spinner loading-lg text-primary"></span>
            </div>
          ) : products?.data?.content && products.data.content.length > 0 ? (
            <div className="grid grid-cols-2 md:grid-cols-3 gap-6">
              {products.data.content.map((product) => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          ) : (
            <div className="text-center py-12">
              <p className="text-lg text-base-content/60 mb-4">
                Aucun produit trouvé
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default SearchPage;
