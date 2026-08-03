import { Link } from 'react-router-dom';

const NotFoundPage = () => {
  return (
    <div className="min-h-[60vh] flex items-center justify-center">
      <div className="text-center">
        <h1 className="text-9xl font-bold text-primary">404</h1>
        <h2 className="text-3xl font-bold mb-4">Page non trouvée</h2>
        <p className="text-base-content/60 mb-8">
          La page que vous recherchez n'existe pas ou a été déplacée.
        </p>
        <Link to="/" className="btn btn-primary btn-lg">
          Retour à l'accueil
        </Link>
      </div>
    </div>
  );
};

export default NotFoundPage;
