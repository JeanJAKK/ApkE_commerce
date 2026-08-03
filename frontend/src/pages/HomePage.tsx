import { Link } from 'react-router-dom';
import { ArrowRight, Star, Truck, Shield, Headphones, CreditCard } from 'lucide-react';
import ProductCard from '@/components/ProductCard';
import { useQuery } from '@tanstack/react-query';
import { productService, categoryService } from '@/services/productService';

const HomePage = () => {
  // Récupérer les produits vedettes
  const { data: featuredProducts } = useQuery({
    queryKey: ['products', 'featured'],
    queryFn: () => productService.getFeatured(8),
  });

  // Récupérer les nouveautés
  const { data: newArrivals } = useQuery({
    queryKey: ['products', 'newArrivals'],
    queryFn: () => productService.getNewArrivals(4),
  });

  // Récupérer les produits en promotion
  const { data: onSaleProducts } = useQuery({
    queryKey: ['products', 'onSale'],
    queryFn: () => productService.getOnSale(4),
  });

  // Récupérer les catégories
  const { data: categories } = useQuery({
    queryKey: ['categories', 'main'],
    queryFn: () => categoryService.getMain(),
  });

  return (
    <div>
      {/* Hero Section */}
      <section className="relative bg-gradient-to-r from-primary/10 to-secondary/10 py-16 lg:py-24">
        <div className="container mx-auto px-4">
          <div className="flex flex-col lg:flex-row items-center gap-12">
            <div className="flex-1 text-center lg:text-left">
              <h1 className="text-4xl lg:text-6xl font-display font-bold mb-6 animate-fade-in">
                Découvrez des <span className="text-primary">produits incroyables</span> à des prix imbattables
              </h1>
              <p className="text-lg text-base-content/70 mb-8 max-w-xl mx-auto lg:mx-0 animate-slide-up">
                Votre destination shopping en ligne. Qualité, variety et service client exceptionnel.
              </p>
              <div className="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start">
                <Link to="/search" className="btn btn-primary btn-lg">
                  Boutique maintenant
                  <ArrowRight className="w-5 h-5 ml-2" />
                </Link>
                <Link to="/search?onSale=true" className="btn btn-outline btn-lg">
                  Voir les promotions
                </Link>
              </div>
            </div>
            <div className="flex-1">
              <div className="relative">
                <div className="w-full aspect-square lg:aspect-[4/3] bg-gradient-to-br from-primary/20 to-secondary/20 rounded-3xl flex items-center justify-center">
                  <span className="text-9xl">🛍️</span>
                </div>
                {/* Badge */}
                <div className="absolute -top-4 -right-4 bg-error text-white px-4 py-2 rounded-full font-bold shadow-lg animate-bounce">
                  -30% OFF
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="py-8 border-b">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 lg:grid-cols-4 gap-6">
            <div className="flex items-center gap-3">
              <div className="p-3 bg-primary/10 rounded-lg">
                <Truck className="w-6 h-6 text-primary" />
              </div>
              <div>
                <p className="font-semibold">Livraison gratuite</p>
                <p className="text-sm text-base-content/60">Commande +50 000 FCFA</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-secondary/10 rounded-lg">
                <Shield className="w-6 h-6 text-secondary" />
              </div>
              <div>
                <p className="font-semibold">Paiement sécurisé</p>
                <p className="text-sm text-base-content/60">100% protégé</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-accent/10 rounded-lg">
                <Headphones className="w-6 h-6 text-accent" />
              </div>
              <div>
                <p className="font-semibold">Support 24/7</p>
                <p className="text-sm text-base-content/60">Toujours disponible</p>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="p-3 bg-primary/10 rounded-lg">
                <CreditCard className="w-6 h-6 text-primary" />
              </div>
              <div>
                <p className="font-semibold">Multi-paiements</p>
                <p className="text-sm text-base-content/60">Cash, Mobile Money</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Categories */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <h2 className="section-title">Nos catégories</h2>
          <div className="grid grid-cols-2 lg:grid-cols-4 gap-6">
            {categories?.data?.map((category) => (
              <Link
                key={category.id}
                to={`/category/${category.slug}`}
                className="group card card-bordered bg-base-200 hover:bg-primary hover:text-white transition-all duration-300 card-hover"
              >
                <div className="card-body items-center text-center p-6">
                  <div className="text-4xl mb-3">
                    {category.icon === 'fa-mobile-alt' && '📱'}
                    {category.icon === 'fa-tshirt' && '👕'}
                    {category.icon === 'fa-home' && '🏠'}
                    {category.icon === 'fa-spa' && '💄'}
                  </div>
                  <h3 className="font-semibold text-lg">{category.name}</h3>
                  <p className="text-sm opacity-70">{category.productCount} produits</p>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Products */}
      <section className="py-16 bg-base-200">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-between mb-8">
            <h2 className="text-3xl font-display font-bold">Produits vedettes</h2>
            <Link to="/search?featured=true" className="btn btn-ghost">
              Voir tout
              <ArrowRight className="w-4 h-4 ml-2" />
            </Link>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {featuredProducts?.data?.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      </section>

      {/* Promotions Banner */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <div className="bg-gradient-to-r from-error to-error/70 rounded-3xl p-8 lg:p-12 text-white">
            <div className="max-w-xl">
              <span className="badge badge-warning text-warning-content mb-4">Offre limitée</span>
              <h2 className="text-3xl lg:text-5xl font-display font-bold mb-4">
                Jusqu'à -50% sur les ventes flash
              </h2>
              <p className="text-lg opacity-90 mb-6">
                Ne manquez pas nos offres exceptionnelles. Profitez-en maintenant!
              </p>
              <Link to="/search?onSale=true" className="btn btn-warning btn-lg">
                Découvrir les offres
                <ArrowRight className="w-5 h-5 ml-2" />
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* New Arrivals */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-between mb-8">
            <h2 className="text-3xl font-display font-bold">Nouveautés</h2>
            <Link to="/search?newArrival=true" className="btn btn-ghost">
              Voir tout
              <ArrowRight className="w-4 h-4 ml-2" />
            </Link>
          </div>
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
            {newArrivals?.data?.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="py-16 bg-base-200">
        <div className="container mx-auto px-4">
          <h2 className="section-title">Ce que disent nos clients</h2>
          <div className="grid md:grid-cols-3 gap-6">
            {[
              {
                name: 'Awa D.',
                rating: 5,
                comment: 'Excellent service et livraison rapide. Je recommande vivement!',
              },
              {
                name: 'Koffi M.',
                rating: 5,
                comment: 'Produits de qualité à des prix compétitifs. Très satisfait.',
              },
              {
                name: 'Akua S.',
                rating: 4,
                comment: 'Bonne expérience d\'achat. Le service client est réactif.',
              },
            ].map((testimonial, index) => (
              <div key={index} className="card bg-base-100 shadow-sm">
                <div className="card-body">
                  <div className="flex gap-1 mb-4">
                    {[...Array(5)].map((_, i) => (
                      <Star
                        key={i}
                        className={`w-5 h-5 ${i < testimonial.rating ? 'text-warning fill-warning' : 'text-base-300'}`}
                      />
                    ))}
                  </div>
                  <p className="text-base-content/80">"{testimonial.comment}"</p>
                  <div className="flex items-center gap-3 mt-4">
                    <div className="w-10 h-10 rounded-full bg-primary text-white flex items-center justify-center font-semibold">
                      {testimonial.name.charAt(0)}
                    </div>
                    <div>
                      <p className="font-semibold">{testimonial.name}</p>
                      <p className="text-sm text-base-content/60">Cliente fidèle</p>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Newsletter */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <div className="max-w-2xl mx-auto text-center">
            <h2 className="text-3xl font-display font-bold mb-4">
              Restez informé
            </h2>
            <p className="text-base-content/70 mb-6">
              Inscrivez-vous à notre newsletter pour recevoir les dernières offres et nouveautés.
            </p>
            <form className="flex flex-col sm:flex-row gap-3 max-w-md mx-auto">
              <input
                type="email"
                placeholder="Votre adresse email"
                className="input input-bordered flex-1"
              />
              <button type="submit" className="btn btn-primary">
                S'inscrire
              </button>
            </form>
          </div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;
