import { Link } from 'react-router-dom';
import { Plus, Edit, Trash2 } from 'lucide-react';

const CategoriesAdminPage = () => {
  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-3xl font-bold">Catégories</h1>
        <button className="btn btn-primary">
          <Plus className="w-4 h-4 mr-2" />
          Nouvelle catégorie
        </button>
      </div>

      <div className="card bg-base-100 card-bordered">
        <div className="card-body">
          <p className="text-base-content/60 text-center py-12">
            Utilisez le backend pour gérer les catégories.
          </p>
        </div>
      </div>
    </div>
  );
};

export default CategoriesAdminPage;
