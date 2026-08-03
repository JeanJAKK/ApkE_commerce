package com.ecommerce.service;

import com.ecommerce.dto.request.SiteSettingsRequest;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.entity.SiteSettings;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.SiteSettingsMapper;
import com.ecommerce.repository.SiteSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotationCacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour la gestion des paramètres du site
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SiteSettingsService {

    private final SiteSettingsRepository siteSettingsRepository;
    private final SiteSettingsMapper siteSettingsMapper;

    /**
     * Récupérer les paramètres du site
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "siteSettings")
    public SiteSettingsResponse getSettings() {
        SiteSettings settings = siteSettingsRepository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Paramètres du site non trouvés"));

        return siteSettingsMapper.toResponse(settings);
    }

    /**
     * Récupérer les paramètres bruts (pour les internes)
     */
    @Transactional(readOnly = true)
    public SiteSettings getSettingsEntity() {
        return siteSettingsRepository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Paramètres du site non trouvés"));
    }

    /**
     * Mettre à jour les paramètres du site
     */
    @Transactional
    @CacheEvict(value = "siteSettings", allEntries = true)
    public SiteSettingsResponse updateSettings(SiteSettingsRequest request) {
        SiteSettings settings = siteSettingsRepository.findAll().stream()
            .findFirst()
            .orElseGet(() -> SiteSettings.builder().build());

        siteSettingsMapper.updateEntity(request, settings);
        settings = siteSettingsRepository.save(settings);

        log.info("Paramètres du site mis à jour");

        return siteSettingsMapper.toResponse(settings);
    }

    /**
     * Vérifier si le mode maintenance est actif
     */
    @Transactional(readOnly = true)
    public boolean isMaintenanceMode() {
        return getSettings().isMaintenanceMode();
    }

    /**
     * Activer/Désactiver le mode maintenance
     */
    @Transactional
    @CacheEvict(value = "siteSettings", allEntries = true)
    public void toggleMaintenanceMode() {
        SiteSettings settings = getSettingsEntity();
        settings.setMaintenanceMode(!settings.isMaintenanceMode());
        siteSettingsRepository.save(settings);
        log.info("Mode maintenance: {}", settings.isMaintenanceMode() ? "activé" : "désactivé");
    }
}
