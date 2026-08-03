import { Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { apiRequest } from '@/services/api';
import { Plus, Edit, Trash2, Archive, Copy } from 'lucide-react';
import type { ApiResponse, PageResponse, Product } from '@/types';

const ProductsAdminPage = () => {
  const { data: products, isLoading } = useQuery({
    queryKey: ['admin-products'],
    queryFn: () => apiRequest.get<ApiResponse<PageResponse<Product>>>('/products', {
      page: 0,
      size: 50,
      sortBy: 'createdAt',
      sortDir: 'desc',
    }),
  });

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-3xl font-bold">Produits</h1>
        <Link to="/admin/products/new" className="btn btn-primary">
          <Plus className="w-4 h-4 mr-2" />
          Nouveau produit
        </Link>
      </div>

      <div className="card bg-base-100 card-bordered">
        <div className="card-body">
          {isLoading ? (
            <div className="flex justify-center py-12">
              <span className="loading loading-spinner loading-lg text-primary"></span>
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="table">
                <thead>
                  <tr>
                    <th>Image</th>
                    <th>Nom</th>
                    <th>SKU</th>
                    <th>Prix</th>
                    <th>Stock</th>
                    <th>Statut</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {products?.data?.content?.map((product) => (
                    <tr key={product.id}>
                      <td>
                        <div className="w-12 h-12 bg-base-200 rounded-lg overflow-hidden">
                          {product.mainImage && (
                            <img src={product.mainImage} alt="" className="w-full h-full object-cover" />
                          )}
                        </div>
                      </td>
                      <td>
                        <div>
                          <p className="font-medium">{product.name}</p>
                          <p className="text-sm text-base-content/60">{product.categoryName}</p>
                        </div>
                      </td>
                      <td className="font-mono text-sm">{product.sku}</td>
                      <td>
                        <p className="font-semibold text-primary">
                          {product.discountedPrice.toLocaleString()} CFA
                        </p>
                        {product.oldPrice && (
                          <p className="text-sm text-base-content/50 line-through">
                            {product.oldPrice.toLocaleString()} CFA
                          </p>
                        )}
                      </td>
                      <td>
                        <span className={product.inStock ? 'text-success' : 'text-error'}>
                          {product.stock}
                        </span>
                      </td>
                      <td>
                        <div className="flex flex-wrap gap-1">
                          {product.featured && <span className="badge badge-sm badge-secondary">Vedette</span>}
                          {product.newArrival && <span className="badge badge-sm badge-primary">Nouveau</span>}
                          {product.onSale && <span className="badge badge-sm badge-error">Promo</span>}
                          {!product.active && <span className="badge badge-sm badge-ghost">Inactif</span>}
                        </div>
                      </td>
                      <td>
                        <div className="flex gap-1">
                          <Link
                            to={`/admin/products/${product.id}/edit`}
                            className="btn btn-ghost btn-sm btn-circle"
                          >
                            <Edit className="w-4 h-4" />
                          </Link>
                          <button className="btn btn-ghost btn-sm btn-circle">
                            <Archive className="w-4 h-4" />
                          </button>
                          <button className="btn btn-ghost btn-sm btn-circle text-error">
                            <Trash2 className="w-4 h-4" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductsAdminPage;
