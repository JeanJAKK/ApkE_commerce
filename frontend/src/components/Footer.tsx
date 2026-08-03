import { Link } from 'react-router-dom';
import { Facebook, Twitter, Instagram, Youtube, Phone, Mail, MapPin } from 'lucide-react';

const Footer = () => {
  return (
    <footer className="bg-base-200 mt-16">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          {/* Logo et description */}
          <div>
            <Link to="/" className="text-2xl font-display font-bold text-primary">
              Ma Boutique
            </Link>
            <p className="mt-4 text-base-content/70">
              Votre destination shopping en ligne. Des produits de qualité à des prix imbattables.
            </p>
            <div className="flex gap-4 mt-4">
              <a href="#" className="btn btn-ghost btn-circle btn-sm">
                <Facebook className="w-5 h-5" />
              </a>
              <a href="#" className="btn btn-ghost btn-circle btn-sm">
                <Twitter className="w-5 h-5" />
              </a>
              <a href="#" className="btn btn-ghost btn-circle btn-sm">
                <Instagram className="w-5 h-5" />
              </a>
              <a href="#" className="btn btn-ghost btn-circle btn-sm">
                <Youtube className="w-5 h-5" />
              </a>
            </div>
          </div>

          {/* Liens rapides */}
          <div>
            <h3 className="font-semibold text-lg mb-4">Liens rapides</h3>
            <ul className="space-y-2">
              <li><Link to="/" className="hover:text-primary transition-colors">Accueil</Link></li>
              <li><Link to="/search" className="hover:text-primary transition-colors">Boutique</Link></li>
              <li><Link to="/search?onSale=true" className="hover:text-primary transition-colors">Promotions</Link></li>
              <li><Link to="/search?newArrival=true" className="hover:text-primary transition-colors">Nouveautés</Link></li>
            </ul>
          </div>

          {/* Service client */}
          <div>
            <h3 className="font-semibold text-lg mb-4">Service client</h3>
            <ul className="space-y-2">
              <li><Link to="/contact" className="hover:text-primary transition-colors">Contact</Link></li>
              <li><Link to="/faq" className="hover:text-primary transition-colors">FAQ</Link></li>
              <li><Link to="/shipping" className="hover:text-primary transition-colors">Livraison</Link></li>
              <li><Link to="/returns" className="hover:text-primary transition-colors">Retours</Link></li>
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h3 className="font-semibold text-lg mb-4">Contact</h3>
            <ul className="space-y-3">
              <li className="flex items-center gap-2">
                <Phone className="w-4 h-4 text-primary" />
                <span>+228 00 00 00 00</span>
              </li>
              <li className="flex items-center gap-2">
                <Mail className="w-4 h-4 text-primary" />
                <span>contact@maboutique.com</span>
              </li>
              <li className="flex items-start gap-2">
                <MapPin className="w-4 h-4 text-primary mt-1" />
                <span>Lomé, Togo</span>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="border-t border-base-300 mt-8 pt-8">
          <div className="flex flex-col md:flex-row justify-between items-center gap-4">
            <p className="text-sm text-base-content/60">
              © 2024 Ma Boutique. Tous droits réservés.
            </p>
            <div className="flex gap-4 text-sm text-base-content/60">
              <Link to="/privacy" className="hover:text-primary">Politique de confidentialité</Link>
              <Link to="/terms" className="hover:text-primary">Conditions d'utilisation</Link>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
