import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, Save } from 'lucide-react';
import toast from 'react-hot-toast';

const ProductFormPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const isEditing = !!id;

  const [formData, setFormData] = useState({
    name: '',
    sku: '',
    price: '',
    oldPrice: '',
    stock: '',
    description: '',
    categoryId: '',
    brand: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // TODO: Implémenter l'API
    toast.success(isEditing ? 'Produit mis à jour' : 'Produit créé');
    navigate('/admin/products');
  };

  return (
    <div>
      <div className="flex items-center gap-4 mb-6">
        <button onClick={() => navigate('/admin/products')} className="btn btn-ghost">
          <ArrowLeft className="w-4 h-4 mr-2" />
          Retour
        </button>
        <h1 className="text-3xl font-bold">{isEditing ? 'Modifier le produit' : 'Nouveau produit'}</h1>
      </div>

      <form onSubmit={handleSubmit} className="card bg-base-100 card-bordered">
        <div className="card-body">
          <div className="grid md:grid-cols-2 gap-6">
            <div className="form-control">
              <label className="label"><span className="label-text">Nom du produit *</span></label>
              <input
                type="text"
                name="name"
                className="input input-bordered"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-control">
              <label className="label"><span className="label-text">SKU *</span></label>
              <input
                type="text"
                name="sku"
                className="input input-bordered"
                value={formData.sku}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-control">
              <label className="label"><span className="label-text">Prix *</span></label>
              <input
                type="number"
                name="price"
                className="input input-bordered"
                value={formData.price}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-control">
              <label className="label"><span className="label-text">Ancien prix</span></label>
              <input
                type="number"
                name="oldPrice"
                className="input input-bordered"
                value={formData.oldPrice}
                onChange={handleChange}
              />
            </div>
            <div className="form-control">
              <label className="label"><span className="label-text">Stock *</span></label>
              <input
                type="number"
                name="stock"
                className="input input-bordered"
                value={formData.stock}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-control">
              <label className="label"><span className="label-text">Catégorie *</span></label>
              <select
                name="categoryId"
                className="select select-bordered"
                value={formData.categoryId}
                onChange={handleChange}
                required
              >
                <option value="">Sélectionner...</option>
              </select>
            </div>
            <div className="form-control">
              <label className="label"><span className="label-text">Marque</span></label>
              <input
                type="text"
                name="brand"
                className="input input-bordered"
                value={formData.brand}
                onChange={handleChange}
              />
            </div>
            <div className="form-control md:col-span-2">
              <label className="label"><span className="label-text">Description</span></label>
              <textarea
                name="description"
                className="textarea textarea-bordered"
                rows={5}
                value={formData.description}
                onChange={handleChange}
              />
            </div>
          </div>

          <div className="flex justify-end gap-4 mt-6">
            <button type="button" onClick={() => navigate('/admin/products')} className="btn btn-ghost">
              Annuler
            </button>
            <button type="submit" className="btn btn-primary">
              <Save className="w-4 h-4 mr-2" />
              Enregistrer
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default ProductFormPage;
