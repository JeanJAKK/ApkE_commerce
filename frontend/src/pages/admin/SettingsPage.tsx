import { useState } from 'react';
import { Save } from 'lucide-react';
import toast from 'react-hot-toast';

const SettingsPage = () => {
  const [formData, setFormData] = useState({
    siteName: 'Ma Boutique',
    siteSlogan: 'Votre destination shopping en ligne',
    contactEmail: 'contact@maboutique.com',
    contactPhone: '+228 00 00 00 00',
    contactAddress: 'Lomé, Togo',
    facebookUrl: '',
    instagramUrl: '',
    twitterUrl: '',
    whatsappNumber: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    toast.success('Paramètres enregistrés');
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-6">Paramètres du site</h1>

      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="card bg-base-100 card-bordered">
          <div className="card-body">
            <h2 className="card-title">Informations du site</h2>
            <div className="grid md:grid-cols-2 gap-4">
              <div className="form-control">
                <label className="label"><span className="label-text">Nom du site</span></label>
                <input
                  type="text"
                  name="siteName"
                  className="input input-bordered"
                  value={formData.siteName}
                  onChange={handleChange}
                />
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">Slogan</span></label>
                <input
                  type="text"
                  name="siteSlogan"
                  className="input input-bordered"
                  value={formData.siteSlogan}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>
        </div>

        <div className="card bg-base-100 card-bordered">
          <div className="card-body">
            <h2 className="card-title">Contact</h2>
            <div className="grid md:grid-cols-2 gap-4">
              <div className="form-control">
                <label className="label"><span className="label-text">Email</span></label>
                <input
                  type="email"
                  name="contactEmail"
                  className="input input-bordered"
                  value={formData.contactEmail}
                  onChange={handleChange}
                />
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">Téléphone</span></label>
                <input
                  type="tel"
                  name="contactPhone"
                  className="input input-bordered"
                  value={formData.contactPhone}
                  onChange={handleChange}
                />
              </div>
              <div className="form-control md:col-span-2">
                <label className="label"><span className="label-text">Adresse</span></label>
                <input
                  type="text"
                  name="contactAddress"
                  className="input input-bordered"
                  value={formData.contactAddress}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>
        </div>

        <div className="card bg-base-100 card-bordered">
          <div className="card-body">
            <h2 className="card-title">Réseaux sociaux</h2>
            <div className="grid md:grid-cols-2 gap-4">
              <div className="form-control">
                <label className="label"><span className="label-text">Facebook</span></label>
                <input
                  type="url"
                  name="facebookUrl"
                  className="input input-bordered"
                  value={formData.facebookUrl}
                  onChange={handleChange}
                />
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">Instagram</span></label>
                <input
                  type="url"
                  name="instagramUrl"
                  className="input input-bordered"
                  value={formData.instagramUrl}
                  onChange={handleChange}
                />
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">Twitter</span></label>
                <input
                  type="url"
                  name="twitterUrl"
                  className="input input-bordered"
                  value={formData.twitterUrl}
                  onChange={handleChange}
                />
              </div>
              <div className="form-control">
                <label className="label"><span className="label-text">WhatsApp</span></label>
                <input
                  type="tel"
                  name="whatsappNumber"
                  className="input input-bordered"
                  value={formData.whatsappNumber}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>
        </div>

        <div className="flex justify-end">
          <button type="submit" className="btn btn-primary">
            <Save className="w-4 h-4 mr-2" />
            Enregistrer les modifications
          </button>
        </div>
      </form>
    </div>
  );
};

export default SettingsPage;
