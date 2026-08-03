# 🛒 Application E-commerce Complète

Une application web e-commerce moderne, professionnelle et responsive pour une petite entreprise ou vendeuse.

## 📋 Fonctionnalités

### Site Public
- **Accueil** : Bannière, produits vedettes, nouveautés, promotions, catégories, avis clients, FAQ
- **Catalogue produits** : Recherche avancée, filtres, pagination
- **Détail produit** : Galerie d'images, description, caractéristiques, produits similaires
- **Discussions** : Commentaires, réponses, likes, signalements
- **Avis clients** : Notes 5 étoiles, photos, répartition des votes
- **Panier** : Ajout, modification, suppression, code promo
- **Commandes** : Multiples modes de paiement (Cash, Mobile Money, Carte bancaire)
- **Favoris** : Liste de souhaits
- **Partage** : WhatsApp, Facebook, X, Telegram

### Dashboard Administrateur
- **Tableau de bord** : Chiffre d'affaires, ventes, commandes, statistiques
- **Gestion des produits** : CRUD complet, import/export CSV, variantes
- **Gestion des catégories** : Catégories et sous-catégories
- **Gestion des commandes** : Statuts, suivi, factures PDF
- **Gestion des clients** : Historique, montants dépensés
- **Gestion des commentaires** : Modération, épinglage
- **Gestion des avis** : Réponses, mise en avant
- **Promotions** : Réductions, coupons, ventes flash
- **Personnalisation** : Logo, couleurs, thème clair/sombre, polices
- **Sauvegarde** : Export de la base de données

## 🛠️ Technologies

### Backend
- **Java 17** + **Spring Boot 3.2**
- **Spring Security** + **JWT**
- **Spring Data JPA** + **PostgreSQL**
- **MapStruct** pour le mapping objet
- **Lombok** pour réduire le code
- **Swagger/OpenAPI** pour la documentation

### Frontend
- **React 18** + **TypeScript**
- **Vite** pour le build
- **Tailwind CSS** + **DaisyUI**
- **React Router** pour la navigation
- **TanStack Query** pour la gestion des données
- **Zustand** pour l'état global
- **Framer Motion** pour les animations

### Infrastructure
- **PostgreSQL** pour la base de données
- **Cloudinary** pour les images
- **Docker** + **Docker Compose** pour le déploiement

## 📁 Architecture du Projet

```
ecommerce-app/
├── backend/                    # API Spring Boot
│   ├── src/main/java/com/ecommerce/
│   │   ├── config/           # Configuration (Security, OpenAPI, Data)
│   │   ├── controller/       # Contrôleurs REST
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── entity/           # Entités JPA
│   │   ├── exception/        # Gestion des erreurs
│   │   ├── mapper/           # MapStruct mappers
│   │   ├── repository/       # Repositories JPA
│   │   ├── security/          # JWT, Filtres
│   │   ├── service/          # Logique métier
│   │   └── specification/     # Specifications JPA
│   └── src/main/resources/
│       └── application.yml    # Configuration
│
├── frontend/                   # Application React
│   ├── src/
│   │   ├── components/       # Composants réutilisables
│   │   ├── pages/            # Pages de l'application
│   │   ├── layouts/          # Mise en page
│   │   ├── context/          # Contextes React
│   │   ├── hooks/            # Hooks personnalisés
│   │   ├── services/         # Appels API
│   │   ├── types/            # Types TypeScript
│   │   └── utils/            # Utilitaires
│   └── ...
│
├── scripts/                   # Scripts SQL
├── docker-compose.yml         # Docker Compose
├── Dockerfile                 # Image Docker
└── .env.example              # Variables d'environnement
```

## 🚀 Installation Locale

### Prérequis
- Java 17+
- Node.js 18+
- Maven 3.8+
- PostgreSQL 14+
- Docker (optionnel)

### Étapes

#### 1. Cloner le projet
```bash
git clone <votre-repo>
cd ecommerce-app
```

#### 2. Configurer l'environnement
```bash
cp .env.example .env
# Éditez .env avec vos paramètres
```

#### 3. Démarrer PostgreSQL
```bash
# Option A: Docker
docker run -d \
  --name ecommerce-postgres \
  -e POSTGRES_DB=ecommerce \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:16-alpine

# Option B: Installation locale
# Créez une base de données 'ecommerce'
```

#### 4. Démarrer le Backend
```bash
cd backend
./mvnw spring-boot:run
# Ou: mvn spring-boot:run
```

#### 5. Démarrer le Frontend
```bash
cd frontend
npm install
npm run dev
```

#### 6. Accéder à l'application
- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Admin**: http://localhost:5173/admin
  - Email: admin@example.com
  - Mot de passe: admin123

## 🐳 Déploiement avec Docker

### Build et exécution
```bash
# Build de l'image
docker build -t ecommerce-app .

# Démarrage avec Docker Compose
docker-compose up -d
```

### Services gratuits recommandés

| Service | Usage | Lien |
|---------|-------|------|
| **Vercel** | Frontend | vercel.com |
| **Render** | Backend | render.com |
| **Koyeb** | Backend alternatif | koyeb.com |
| **Supabase** | PostgreSQL | supabase.com |
| **Neon** | PostgreSQL alternatif | neon.tech |
| **Cloudinary** | Images | cloudinary.com |

## 📖 Documentation API

La documentation Swagger est disponible à:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints principaux

#### Authentification
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion
- `POST /api/auth/refresh` - Rafraîchir le token

#### Produits
- `GET /api/products` - Liste des produits
- `GET /api/products/{id}` - Détail produit
- `POST /api/products/search` - Recherche avancée
- `GET /api/products/featured` - Produits vedettes

#### Catégories
- `GET /api/categories` - Liste des catégories
- `GET /api/categories/{id}` - Détail catégorie

#### Commandes
- `POST /api/orders` - Créer une commande
- `GET /api/orders/{id}` - Détail commande
- `GET /api/orders/user/{userId}` - Commandes utilisateur

#### Admin
- `GET /api/admin/dashboard` - Tableau de bord
- `POST /api/admin/products` - Créer produit
- `PUT /api/admin/products/{id}` - Modifier produit

## 🧪 Tests

### Backend
```bash
cd backend
./mvnw test
```

## 📝 Licence

Ce projet est sous licence MIT.

## 🤝 Contribution

Les contributions sont les bienvenues! 

1. Fork le projet
2. Créez une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit (`git commit -m 'Add AmazingFeature'`)
4. Push (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

---

Développé avec ❤️ pour les entrepreneurs et PME africains.
