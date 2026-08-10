import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Search,
  ShoppingCart,
  User,
  Heart,
  Menu,
  X,
  Sun,
  Moon,
} from "lucide-react";
import { useCartStore } from "@/context/cartStore";
import { useAuthStore } from "@/context/authStore";

const Header = () => {
  const navigate = useNavigate();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [isDarkMode, setIsDarkMode] = useState(false);

  const { items, openCart } = useCartStore();
  const { isAuthenticated, isAdmin, logout, user } = useAuthStore();

  const cartItemCount = items.reduce((sum, item) => sum + item.quantity, 0);

  useEffect(() => {
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme === "dark") {
      setIsDarkMode(true);
      document.documentElement.setAttribute("data-theme", "dark");
    }
  }, []);

  const toggleTheme = () => {
    const newTheme = isDarkMode ? "light" : "dark";
    setIsDarkMode(!isDarkMode);
    document.documentElement.setAttribute("data-theme", newTheme);
    localStorage.setItem("theme", newTheme);
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
      setIsSearchOpen(false);
      setSearchQuery("");
    }
  };

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <header className="sticky top-0 z-50 bg-base-100 shadow-sm">
      {/* Top bar */}
      <div className="bg-primary text-white text-sm py-2">
        <div className="container mx-auto px-4 text-center">
          Livraison gratuite pour toute commande de +50 000 FCFA 🚚
        </div>
      </div>

      {/* Main header */}
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between py-4 gap-4">
          {/* Logo */}
          <div className="flex-shrink-0">
            <Link
              to="/"
              className="text-2xl font-display font-bold text-primary"
            >
              Ma Boutique
            </Link>
          </div>

          {/* Desktop Navigation */}
          <div className="hidden lg:flex flex-shrink-0">
            <ul className="menu menu-horizontal px-1 gap-4">
              <li>
                <Link to="/" className="font-medium">
                  Accueil
                </Link>
              </li>
              <li className="dropdown dropdown-hover">
                <details>
                  <summary className="font-medium">Catégories</summary>
                  <ul className="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-52">
                    <li>
                      <Link to="/category/electronique">Électronique</Link>
                    </li>
                    <li>
                      <Link to="/category/vetements">Vêtements</Link>
                    </li>
                    <li>
                      <Link to="/category/maison">Maison</Link>
                    </li>
                    <li>
                      <Link to="/category/beaute">Beauté</Link>
                    </li>
                  </ul>
                </details>
              </li>
            </ul>
          </div>

          {/* Right actions */}
          <div className="flex items-center gap-2 flex-shrink-0">
            {/* Search */}
            <div className="hidden xl:block">
              <form onSubmit={handleSearch} className="join">
                <input
                  type="text"
                  placeholder="Rechercher..."
                  className="input input-bordered join-item w-40 lg:w-56"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
                <button type="submit" className="btn btn-primary join-item">
                  <Search className="w-4 h-4" />
                </button>
              </form>
            </div>

            {/* Mobile/tablet search button */}
            <button
              className="btn btn-ghost btn-circle xl:hidden"
              onClick={() => setIsSearchOpen(!isSearchOpen)}
            >
              <Search className="w-5 h-5" />
            </button>

            {/* Theme toggle */}
            <button className="btn btn-ghost btn-circle" onClick={toggleTheme}>
              {isDarkMode ? (
                <Sun className="w-5 h-5" />
              ) : (
                <Moon className="w-5 h-5" />
              )}
            </button>

            {/* Favorites */}
            {isAuthenticated && (
              <Link
                to="/favorites"
                className="btn btn-ghost btn-circle hidden md:flex"
              >
                <Heart className="w-5 h-5" />
              </Link>
            )}

            {/* User */}
            {isAuthenticated ? (
              <div className="dropdown dropdown-end">
                <label tabIndex={0} className="btn btn-ghost btn-circle avatar">
                  <div className="w-10 rounded-full bg-primary text-white flex items-center justify-center">
                    <User className="w-5 h-5" />
                  </div>
                </label>
                <ul
                  tabIndex={0}
                  className="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52"
                >
                  <li className="menu-title">
                    <span>{user?.fullName || "Utilisateur"}</span>
                  </li>
                  <li>
                    <Link to="/profile">Mon Profil</Link>
                  </li>
                  <li>
                    <Link to="/orders">Mes Commandes</Link>
                  </li>
                  {isAuthenticated && (
                    <li>
                      <Link to="/favorites">Mes Favoris</Link>
                    </li>
                  )}
                  {isAdmin && (
                    <li>
                      <Link
                        to="/admin/dashboard"
                        className="text-primary font-semibold"
                      >
                        Dashboard Admin
                      </Link>
                    </li>
                  )}
                  <li>
                    <button onClick={handleLogout}>Déconnexion</button>
                  </li>
                </ul>
              </div>
            ) : (
              <Link to="/login" className="btn btn-ghost">
                <User className="w-5 h-5 mr-2" />
                <span className="hidden md:inline">Connexion</span>
              </Link>
            )}

            {/* Cart */}
            <button className="btn btn-ghost btn-circle" onClick={openCart}>
              <div className="indicator">
                <ShoppingCart className="w-5 h-5" />
                {cartItemCount > 0 && (
                  <span className="badge badge-primary badge-sm indicator-item">
                    {cartItemCount}
                  </span>
                )}
              </div>
            </button>

            {/* Mobile menu */}
            <button
              className="btn btn-ghost lg:hidden"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              {isMenuOpen ? (
                <X className="w-6 h-6" />
              ) : (
                <Menu className="w-6 h-6" />
              )}
            </button>
          </div>
        </div>

        {/* Mobile search bar */}
        {isSearchOpen && (
          <div className="pb-4 xl:hidden animate-slide-down">
            <form onSubmit={handleSearch} className="join w-full">
              <input
                type="text"
                placeholder="Rechercher un produit..."
                className="input input-bordered join-item flex-1"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
              <button type="submit" className="btn btn-primary join-item">
                <Search className="w-4 h-4" />
              </button>
            </form>
          </div>
        )}

        {/* Mobile menu */}
        {isMenuOpen && (
          <div className="lg:hidden pb-4 animate-slide-down">
            <ul className="menu">
              <li>
                <Link to="/" onClick={() => setIsMenuOpen(false)}>
                  Accueil
                </Link>
              </li>
              <li>
                <details>
                  <summary>Catégories</summary>
                  <ul>
                    <li>
                      <Link
                        to="/category/electronique"
                        onClick={() => setIsMenuOpen(false)}
                      >
                        Électronique
                      </Link>
                    </li>
                    <li>
                      <Link
                        to="/category/vetements"
                        onClick={() => setIsMenuOpen(false)}
                      >
                        Vêtements
                      </Link>
                    </li>
                    <li>
                      <Link
                        to="/category/maison"
                        onClick={() => setIsMenuOpen(false)}
                      >
                        Maison
                      </Link>
                    </li>
                    <li>
                      <Link
                        to="/category/beaute"
                        onClick={() => setIsMenuOpen(false)}
                      >
                        Beauté
                      </Link>
                    </li>
                  </ul>
                </details>
              </li>
              {isAuthenticated && (
                <li>
                  <Link to="/favorites" onClick={() => setIsMenuOpen(false)}>
                    Favoris
                  </Link>
                </li>
              )}
            </ul>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
