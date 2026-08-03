import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '@/services/authService';
import { useAuthStore } from '@/context/authStore';
import toast from 'react-hot-toast';
import { Mail, Lock, User, Phone, Eye, EyeOff } from 'lucide-react';

const RegisterPage = () => {
  const navigate = useNavigate();
  const { login } = useAuthStore();
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      toast.error('Les mots de passe ne correspondent pas');
      return;
    }

    if (formData.password.length < 8) {
      toast.error('Le mot de passe doit contenir au moins 8 caractères');
      return;
    }

    setIsLoading(true);

    try {
      const response = await authService.register({
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        phone: formData.phone || undefined,
        password: formData.password,
      });
      login(response.data);
      toast.success('Inscription réussie!');
      navigate('/');
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Erreur lors de l\'inscription');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-base-200 py-12 px-4">
      <div className="card bg-base-100 w-full max-w-md shadow-xl">
        <div className="card-body p-8">
          <div className="text-center mb-8">
            <h1 className="text-3xl font-display font-bold text-primary">Ma Boutique</h1>
            <p className="text-base-content/60 mt-2">Créez votre compte</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div className="form-control">
                <label className="label"><span className="label-text">Prénom</span></label>
                <div className="relative">
                  <User className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                  <input
                    type="text"
                    name="firstName"
                    className="input input-bordered w-full pl-10"
                    value={formData.firstName}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">Nom</span></label>
                <input
                  type="text"
                  name="lastName"
                  className="input input-bordered w-full"
                  value={formData.lastName}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-control">
              <label className="label"><span className="label-text">Email</span></label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                <input
                  type="email"
                  name="email"
                  className="input input-bordered w-full pl-10"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-control">
              <label className="label"><span className="label-text">Téléphone (optionnel)</span></label>
              <div className="relative">
                <Phone className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                <input
                  type="tel"
                  name="phone"
                  className="input input-bordered w-full pl-10"
                  value={formData.phone}
                  onChange={handleChange}
                />
              </div>
            </div>

            <div className="form-control">
              <label className="label"><span className="label-text">Mot de passe</span></label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                <input
                  type={showPassword ? 'text' : 'password'}
                  name="password"
                  className="input input-bordered w-full pl-10"
                  value={formData.password}
                  onChange={handleChange}
                  required
                  minLength={8}
                />
                <button
                  type="button"
                  className="absolute right-3 top-1/2 -translate-y-1/2"
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                </button>
              </div>
            </div>

            <div className="form-control">
              <label className="label"><span className="label-text">Confirmer le mot de passe</span></label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                <input
                  type={showPassword ? 'text' : 'password'}
                  name="confirmPassword"
                  className="input input-bordered w-full pl-10"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <button
              type="submit"
              className="btn btn-primary w-full"
              disabled={isLoading}
            >
              {isLoading ? <span className="loading loading-spinner"></span> : 'Créer un compte'}
            </button>
          </form>

          <p className="text-center mt-4">
            Déjà un compte?{' '}
            <Link to="/login" className="text-primary font-semibold">
              Se connecter
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
