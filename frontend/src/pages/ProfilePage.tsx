import { useAuthStore } from '@/context/authStore';
import { User, Mail, Phone, MapPin, Edit } from 'lucide-react';

const ProfilePage = () => {
  const { user } = useAuthStore();

  if (!user) return null;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-8">Mon Profil</h1>

      <div className="grid lg:grid-cols-3 gap-8">
        <div className="card bg-base-100 card-bordered p-6">
          <div className="text-center mb-6">
            <div className="w-24 h-24 rounded-full bg-primary text-white flex items-center justify-center text-3xl font-bold mx-auto mb-4">
              {user.firstName?.charAt(0)}{user.lastName?.charAt(0)}
            </div>
            <h2 className="text-xl font-bold">{user.fullName}</h2>
            <p className="text-base-content/60">{user.email}</p>
          </div>
          <button className="btn btn-primary w-full">
            <Edit className="w-4 h-4 mr-2" />
            Modifier le profil
          </button>
        </div>

        <div className="lg:col-span-2 space-y-6">
          <div className="card bg-base-100 card-bordered p-6">
            <h3 className="font-semibold text-lg mb-4">Informations personnelles</h3>
            <div className="space-y-4">
              <div className="flex items-center gap-3">
                <User className="w-5 h-5 text-primary" />
                <div>
                  <p className="text-sm text-base-content/60">Nom complet</p>
                  <p className="font-medium">{user.fullName}</p>
                </div>
              </div>
              <div className="flex items-center gap-3">
                <Mail className="w-5 h-5 text-primary" />
                <div>
                  <p className="text-sm text-base-content/60">Email</p>
                  <p className="font-medium">{user.email}</p>
                </div>
              </div>
              {user.phone && (
                <div className="flex items-center gap-3">
                  <Phone className="w-5 h-5 text-primary" />
                  <div>
                    <p className="text-sm text-base-content/60">Téléphone</p>
                    <p className="font-medium">{user.phone}</p>
                  </div>
                </div>
              )}
              {user.address && (
                <div className="flex items-center gap-3">
                  <MapPin className="w-5 h-5 text-primary" />
                  <div>
                    <p className="text-sm text-base-content/60">Adresse</p>
                    <p className="font-medium">{user.address}</p>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
