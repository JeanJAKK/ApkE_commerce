package com.ecommerce.config;

import com.ecommerce.entity.*;
import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Initialisation des données par défaut
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SiteSettingsRepository siteSettingsRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin-email:admin@example.com}")
    private String adminEmail;

    @Value("${app.admin-password:admin123}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeAdminUser();
        initializeCategories();
        initializeSiteSettings();
        log.info("Initialisation des données terminée avec succès");
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role userRole = Role.builder()
                .name(ERole.ROLE_USER)
                .description("Rôle utilisateur standard")
                .build();

            Role adminRole = Role.builder()
                .name(ERole.ROLE_ADMIN)
                .description("Rôle administrateur")
                .build();

            Role moderatorRole = Role.builder()
                .name(ERole.ROLE_MODERATOR)
                .description("Rôle modérateur")
                .build();

            roleRepository.save(userRole);
            roleRepository.save(adminRole);
            roleRepository.save(moderatorRole);

            log.info("Rôles initialisés");
        }
    }

    private void initializeAdminUser() {
        if (!userRepository.existsByEmail(adminEmail)) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Rôle admin non trouvé"));

            User admin = User.builder()
                .firstName("Admin")
                .lastName("System")
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .enabled(true)
                .blocked(false)
                .roles(Set.of(adminRole))
                .build();

            userRepository.save(admin);
            log.info("Utilisateur administrateur créé: {}", adminEmail);
        }
    }

    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            // Catégorie principale
            Category electronics = Category.builder()
                .name("Électronique")
                .slug("electronique")
                .description("Tous les produits électroniques")
                .icon("fa-mobile-alt")
                .position(1)
                .active(true)
                .build();

            Category clothing = Category.builder()
                .name("Vêtements")
                .slug("vetements")
                .description("Mode et vêtements")
                .icon("fa-tshirt")
                .position(2)
                .active(true)
                .build();

            Category home = Category.builder()
                .name("Maison")
                .slug("maison")
                .description("Articles pour la maison")
                .icon("fa-home")
                .position(3)
                .active(true)
                .build();

            Category beauty = Category.builder()
                .name("Beauté")
                .slug("beaute")
                .description("Produits de beauté et soins")
                .icon("fa-spa")
                .position(4)
                .active(true)
                .build();

            categoryRepository.save(electronics);
            categoryRepository.save(clothing);
            categoryRepository.save(home);
            categoryRepository.save(beauty);

            // Sous-catégories
            Category smartphones = Category.builder()
                .name("Smartphones")
                .slug("smartphones")
                .description("Smartphones et accessoires")
                .icon("fa-mobile-alt")
                .position(1)
                .active(true)
                .parent(electronics)
                .build();

            Category laptops = Category.builder()
                .name("Ordinateurs")
                .slug("ordinateurs")
                .description("Ordinateurs portables et fixes")
                .icon("fa-laptop")
                .position(2)
                .active(true)
                .parent(electronics)
                .build();

            Category mensClothing = Category.builder()
                .name("Homme")
                .slug("homme")
                .description("Vêtements pour hommes")
                .icon("fa-male")
                .position(1)
                .active(true)
                .parent(clothing)
                .build();

            Category womensClothing = Category.builder()
                .name("Femme")
                .slug("femme")
                .description("Vêtements pour femmes")
                .icon("fa-female")
                .position(2)
                .active(true)
                .parent(clothing)
                .build();

            categoryRepository.save(smartphones);
            categoryRepository.save(laptops);
            categoryRepository.save(mensClothing);
            categoryRepository.save(womensClothing);

            log.info("Catégories initialisées");
        }
    }

    private void initializeSiteSettings() {
        if (siteSettingsRepository.count() == 0) {
            SiteSettings settings = SiteSettings.builder()
                .siteName("Ma Boutique")
                .siteSlogan("Votre destination shopping en ligne")
                .description("Boutique en ligne proposant des produits de qualité")
                .primaryColor("#3b82f6")
                .secondaryColor("#10b981")
                .accentColor("#f59e0b")
                .darkModeEnabled(false)
                .primaryFont("Inter")
                .secondaryFont("Poppins")
                .contactEmail("contact@maboutique.com")
                .contactPhone("+228 00 00 00 00")
                .contactAddress("Lomé, Togo")
                .contactHours("Lun-Sam: 8h-20h")
                .facebookUrl("https://facebook.com")
                .twitterUrl("https://twitter.com")
                .instagramUrl("https://instagram.com")
                .whatsappNumber("+22800000000")
                .freeShippingEnabled(true)
                .freeShippingThreshold(50000)
                .defaultShippingCost(2500)
                .taxRate(0)
                .taxIncluded(true)
                .currencyCode("XOF")
                .currencySymbol("CFA")
                .welcomeMessage("Bienvenue sur notre boutique en ligne !")
                .build();

            siteSettingsRepository.save(settings);
            log.info("Paramètres du site initialisés");
        }
    }
}
