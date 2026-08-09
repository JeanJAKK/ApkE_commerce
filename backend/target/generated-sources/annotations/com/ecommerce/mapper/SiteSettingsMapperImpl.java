package com.ecommerce.mapper;

import com.ecommerce.dto.request.SiteSettingsRequest;
import com.ecommerce.dto.response.SiteSettingsResponse;
import com.ecommerce.entity.SiteSettings;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:49+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class SiteSettingsMapperImpl implements SiteSettingsMapper {

    @Override
    public SiteSettings toEntity(SiteSettingsRequest request) {
        if ( request == null ) {
            return null;
        }

        SiteSettings.SiteSettingsBuilder<?, ?> siteSettings = SiteSettings.builder();

        siteSettings.siteName( request.getSiteName() );
        siteSettings.siteSlogan( request.getSiteSlogan() );
        siteSettings.logo( request.getLogo() );
        siteSettings.favicon( request.getFavicon() );
        siteSettings.description( request.getDescription() );
        siteSettings.primaryColor( request.getPrimaryColor() );
        siteSettings.secondaryColor( request.getSecondaryColor() );
        siteSettings.accentColor( request.getAccentColor() );
        if ( request.getDarkModeEnabled() != null ) {
            siteSettings.darkModeEnabled( request.getDarkModeEnabled() );
        }
        siteSettings.primaryFont( request.getPrimaryFont() );
        siteSettings.secondaryFont( request.getSecondaryFont() );
        siteSettings.heroBanner( request.getHeroBanner() );
        siteSettings.promoBanner1( request.getPromoBanner1() );
        siteSettings.promoBanner2( request.getPromoBanner2() );
        siteSettings.promoBanner3( request.getPromoBanner3() );
        siteSettings.contactEmail( request.getContactEmail() );
        siteSettings.contactPhone( request.getContactPhone() );
        siteSettings.contactAddress( request.getContactAddress() );
        siteSettings.contactHours( request.getContactHours() );
        siteSettings.facebookUrl( request.getFacebookUrl() );
        siteSettings.twitterUrl( request.getTwitterUrl() );
        siteSettings.instagramUrl( request.getInstagramUrl() );
        siteSettings.youtubeUrl( request.getYoutubeUrl() );
        siteSettings.whatsappNumber( request.getWhatsappNumber() );
        siteSettings.telegramUrl( request.getTelegramUrl() );
        siteSettings.privacyPolicy( request.getPrivacyPolicy() );
        siteSettings.termsConditions( request.getTermsConditions() );
        siteSettings.refundPolicy( request.getRefundPolicy() );
        if ( request.getMaintenanceMode() != null ) {
            siteSettings.maintenanceMode( request.getMaintenanceMode() );
        }
        siteSettings.maintenanceMessage( request.getMaintenanceMessage() );
        if ( request.getFreeShippingEnabled() != null ) {
            siteSettings.freeShippingEnabled( request.getFreeShippingEnabled() );
        }
        if ( request.getFreeShippingThreshold() != null ) {
            siteSettings.freeShippingThreshold( request.getFreeShippingThreshold() );
        }
        if ( request.getDefaultShippingCost() != null ) {
            siteSettings.defaultShippingCost( request.getDefaultShippingCost() );
        }
        if ( request.getTaxRate() != null ) {
            siteSettings.taxRate( request.getTaxRate() );
        }
        if ( request.getTaxIncluded() != null ) {
            siteSettings.taxIncluded( request.getTaxIncluded() );
        }
        siteSettings.currencyCode( request.getCurrencyCode() );
        siteSettings.currencySymbol( request.getCurrencySymbol() );
        siteSettings.welcomeMessage( request.getWelcomeMessage() );

        return siteSettings.build();
    }

    @Override
    public void updateEntity(SiteSettingsRequest request, SiteSettings settings) {
        if ( request == null ) {
            return;
        }

        settings.setSiteName( request.getSiteName() );
        settings.setSiteSlogan( request.getSiteSlogan() );
        settings.setLogo( request.getLogo() );
        settings.setFavicon( request.getFavicon() );
        settings.setDescription( request.getDescription() );
        settings.setPrimaryColor( request.getPrimaryColor() );
        settings.setSecondaryColor( request.getSecondaryColor() );
        settings.setAccentColor( request.getAccentColor() );
        if ( request.getDarkModeEnabled() != null ) {
            settings.setDarkModeEnabled( request.getDarkModeEnabled() );
        }
        settings.setPrimaryFont( request.getPrimaryFont() );
        settings.setSecondaryFont( request.getSecondaryFont() );
        settings.setHeroBanner( request.getHeroBanner() );
        settings.setPromoBanner1( request.getPromoBanner1() );
        settings.setPromoBanner2( request.getPromoBanner2() );
        settings.setPromoBanner3( request.getPromoBanner3() );
        settings.setContactEmail( request.getContactEmail() );
        settings.setContactPhone( request.getContactPhone() );
        settings.setContactAddress( request.getContactAddress() );
        settings.setContactHours( request.getContactHours() );
        settings.setFacebookUrl( request.getFacebookUrl() );
        settings.setTwitterUrl( request.getTwitterUrl() );
        settings.setInstagramUrl( request.getInstagramUrl() );
        settings.setYoutubeUrl( request.getYoutubeUrl() );
        settings.setWhatsappNumber( request.getWhatsappNumber() );
        settings.setTelegramUrl( request.getTelegramUrl() );
        settings.setPrivacyPolicy( request.getPrivacyPolicy() );
        settings.setTermsConditions( request.getTermsConditions() );
        settings.setRefundPolicy( request.getRefundPolicy() );
        if ( request.getMaintenanceMode() != null ) {
            settings.setMaintenanceMode( request.getMaintenanceMode() );
        }
        settings.setMaintenanceMessage( request.getMaintenanceMessage() );
        if ( request.getFreeShippingEnabled() != null ) {
            settings.setFreeShippingEnabled( request.getFreeShippingEnabled() );
        }
        if ( request.getFreeShippingThreshold() != null ) {
            settings.setFreeShippingThreshold( request.getFreeShippingThreshold() );
        }
        if ( request.getDefaultShippingCost() != null ) {
            settings.setDefaultShippingCost( request.getDefaultShippingCost() );
        }
        if ( request.getTaxRate() != null ) {
            settings.setTaxRate( request.getTaxRate() );
        }
        if ( request.getTaxIncluded() != null ) {
            settings.setTaxIncluded( request.getTaxIncluded() );
        }
        settings.setCurrencyCode( request.getCurrencyCode() );
        settings.setCurrencySymbol( request.getCurrencySymbol() );
        settings.setWelcomeMessage( request.getWelcomeMessage() );
    }

    @Override
    public SiteSettingsResponse toResponse(SiteSettings settings) {
        if ( settings == null ) {
            return null;
        }

        SiteSettingsResponse.SiteSettingsResponseBuilder siteSettingsResponse = SiteSettingsResponse.builder();

        siteSettingsResponse.id( settings.getId() );
        siteSettingsResponse.siteName( settings.getSiteName() );
        siteSettingsResponse.siteSlogan( settings.getSiteSlogan() );
        siteSettingsResponse.logo( settings.getLogo() );
        siteSettingsResponse.favicon( settings.getFavicon() );
        siteSettingsResponse.description( settings.getDescription() );
        siteSettingsResponse.primaryColor( settings.getPrimaryColor() );
        siteSettingsResponse.secondaryColor( settings.getSecondaryColor() );
        siteSettingsResponse.accentColor( settings.getAccentColor() );
        siteSettingsResponse.darkModeEnabled( settings.isDarkModeEnabled() );
        siteSettingsResponse.primaryFont( settings.getPrimaryFont() );
        siteSettingsResponse.secondaryFont( settings.getSecondaryFont() );
        siteSettingsResponse.heroBanner( settings.getHeroBanner() );
        siteSettingsResponse.promoBanner1( settings.getPromoBanner1() );
        siteSettingsResponse.promoBanner2( settings.getPromoBanner2() );
        siteSettingsResponse.promoBanner3( settings.getPromoBanner3() );
        siteSettingsResponse.contactEmail( settings.getContactEmail() );
        siteSettingsResponse.contactPhone( settings.getContactPhone() );
        siteSettingsResponse.contactAddress( settings.getContactAddress() );
        siteSettingsResponse.contactHours( settings.getContactHours() );
        siteSettingsResponse.facebookUrl( settings.getFacebookUrl() );
        siteSettingsResponse.twitterUrl( settings.getTwitterUrl() );
        siteSettingsResponse.instagramUrl( settings.getInstagramUrl() );
        siteSettingsResponse.youtubeUrl( settings.getYoutubeUrl() );
        siteSettingsResponse.whatsappNumber( settings.getWhatsappNumber() );
        siteSettingsResponse.telegramUrl( settings.getTelegramUrl() );
        siteSettingsResponse.privacyPolicy( settings.getPrivacyPolicy() );
        siteSettingsResponse.termsConditions( settings.getTermsConditions() );
        siteSettingsResponse.refundPolicy( settings.getRefundPolicy() );
        siteSettingsResponse.maintenanceMode( settings.isMaintenanceMode() );
        siteSettingsResponse.maintenanceMessage( settings.getMaintenanceMessage() );
        siteSettingsResponse.freeShippingEnabled( settings.isFreeShippingEnabled() );
        siteSettingsResponse.freeShippingThreshold( settings.getFreeShippingThreshold() );
        siteSettingsResponse.defaultShippingCost( settings.getDefaultShippingCost() );
        siteSettingsResponse.taxRate( settings.getTaxRate() );
        siteSettingsResponse.taxIncluded( settings.isTaxIncluded() );
        siteSettingsResponse.currencyCode( settings.getCurrencyCode() );
        siteSettingsResponse.currencySymbol( settings.getCurrencySymbol() );
        siteSettingsResponse.welcomeMessage( settings.getWelcomeMessage() );

        return siteSettingsResponse.build();
    }
}
