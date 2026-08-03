package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application E-commerce principale
 * 
 * Cette application Spring Boot fournit une API REST complète pour un site e-commerce.
 * Elle inclut:
 * - Gestion des produits, catégories, commandes
 * - Authentification JWT
 * - API Admin pour la gestion du site
 * - Intégration Cloudinary pour les images
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
