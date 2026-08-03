-- Script d'initialisation de la base de données e-commerce
-- Ce script est exécuté automatiquement lors de la première création du conteneur PostgreSQL

-- Activer les extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Les tables seront créées automatiquement par Hibernate lors du premier démarrage
-- Ce script est utilisé pour les configurations initiales si nécessaire

-- Index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category_id);
CREATE INDEX IF NOT EXISTS idx_products_slug ON products(slug);
CREATE INDEX IF NOT EXISTS idx_products_sku ON products(sku);
CREATE INDEX IF NOT EXISTS idx_products_featured ON products(featured);
CREATE INDEX IF NOT EXISTS idx_products_new_arrival ON products(new_arrival);
CREATE INDEX IF NOT EXISTS idx_products_on_sale ON products(on_sale);
CREATE INDEX IF NOT EXISTS idx_products_price ON products(price);

CREATE INDEX IF NOT EXISTS idx_orders_user ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_order_number ON orders(order_number);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_created_at ON orders(created_at);

CREATE INDEX IF NOT EXISTS idx_categories_slug ON categories(slug);
CREATE INDEX IF NOT EXISTS idx_categories_parent ON categories(parent_id);

CREATE INDEX IF NOT EXISTS idx_reviews_product ON reviews(product_id);
CREATE INDEX IF NOT EXISTS idx_reviews_user ON reviews(user_id);

CREATE INDEX IF NOT EXISTS idx_comments_product ON comments(product_id);
CREATE INDEX IF NOT EXISTS idx_comments_user ON comments(user_id);
CREATE INDEX IF NOT EXISTS idx_comments_parent ON comments(parent_id);

CREATE INDEX IF NOT EXISTS idx_cart_items_user ON cart_items(user_id);
CREATE INDEX IF NOT EXISTS idx_cart_items_product ON cart_items(product_id);

CREATE INDEX IF NOT EXISTS idx_favorites_user ON favorites(user_id);
CREATE INDEX IF NOT EXISTS idx_favorites_product ON favorites(product_id);

CREATE INDEX IF NOT EXISTS idx_notifications_user ON notifications(user_id);
CREATE INDEX IF NOT EXISTS idx_notifications_read ON notifications(read);

CREATE INDEX IF NOT EXISTS idx_promotions_code ON promotions(code);
CREATE INDEX IF NOT EXISTS idx_promotions_active ON promotions(active);
CREATE INDEX IF NOT EXISTS idx_promotions_dates ON promotions(start_date, end_date);

-- Commentaire sur les tables
COMMENT ON TABLE users IS 'Table des utilisateurs du système';
COMMENT ON TABLE products IS 'Table des produits du catalogue';
COMMENT ON TABLE categories IS 'Table des catégories de produits';
COMMENT ON TABLE orders IS 'Table des commandes';
COMMENT ON TABLE order_items IS 'Table des articles de commande';
COMMENT ON TABLE reviews IS 'Table des avis clients';
COMMENT ON TABLE comments IS 'Table des commentaires de discussion';
COMMENT ON TABLE promotions IS 'Table des promotions et coupons';
COMMENT ON TABLE notifications IS 'Table des notifications';
COMMENT ON TABLE site_settings IS 'Table des paramètres du site';

-- Message de confirmation
DO $$
BEGIN
    RAISE NOTICE 'Base de données e-commerce initialisée avec succès';
END $$;
