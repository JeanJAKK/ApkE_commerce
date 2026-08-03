import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '@/services/authService';
import { useAuthStore } from '@/context/authStore';
import toast from 'react-hot-toast';
import { Mail, Lock, Eye, EyeOff } from 'lucide-react';

const LoginPage = () => {
  const navigate = useNavigate();
  const { login } = useAuthStore();
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const response = await authService.login(formData);
      login(response.data);
      toast.success('Connexion réussie!');
      navigate('/');
    } catch (error: any) {
      toast.error(error.response?.data?.message || 'Email ou mot de passe incorrect');
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
            <p className="text-base-content/60 mt-2">Connectez-vous à votre compte</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="form-control">
              <label className="label"><span className="label-text">Email</span></label>
              <div className="relative">
                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                <input
                  type="email"
                  name="email"
                  className="input input-bordered w-full pl-10"
                  placeholder="votre@email.com"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-control">
              <label className="label">
                <span className="label-text">Mot de passe</span>
                <Link to="/forgot-password" className="label-text-alt text-primary">
                  Mot de passe oublié?
                </Link>
              </label>
              <div className="relative">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-base-content/40" />
                <input
                  type={showPassword ? 'text' : 'password'}
                  name="password"
                  className="input input-bordered w-full pl-10"
                  placeholder="••••••••"
                  value={formData.password}
                  onChange={handleChange}
                  required
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

            <button
              type="submit"
              className="btn btn-primary w-full"
              disabled={isLoading}
            >
              {isLoading ? <span className="loading loading-spinner"></span> : 'Se connecter'}
            </button>
          </form>

          <div className="divider">ou</div>

          <p className="text-center">
            Pas encore de compte?{' '}
            <Link to="/register" className="text-primary font-semibold">
              Créer un compte
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
