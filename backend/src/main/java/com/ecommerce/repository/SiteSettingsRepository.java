package com.ecommerce.repository;

import com.ecommerce.entity.SiteSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les opérations sur les paramètres du site
 */
@Repository
public interface SiteSettingsRepository extends JpaRepository<SiteSettings, Long> {
}
