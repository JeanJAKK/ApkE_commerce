package com.ecommerce.specification;

import com.ecommerce.dto.request.SearchRequest;
import com.ecommerce.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Spécifications JPA pour les recherches de produits
 */
public class ProductSpecification {

    public static Specification<Product> buildSpecification(SearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtre par actif et non archivé
            predicates.add(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("active"), true),
                criteriaBuilder.equal(root.get("archived"), false)
            ));

            // Recherche textuelle
            if (request.getQuery() != null && !request.getQuery().isBlank()) {
                String searchTerm = "%" + request.getQuery().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), searchTerm)
                ));
            }

            // Filtre par catégorie
            if (request.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), request.getCategoryId()));
            }

            // Filtre par sous-catégorie
            if (request.getSubcategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("subcategory").get("id"), request.getSubcategoryId()));
            }

            // Filtre par marque
            if (request.getBrand() != null && !request.getBrand().isBlank()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), request.getBrand().toLowerCase()));
            }

            // Filtre par prix minimum
            if (request.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }

            // Filtre par prix maximum
            if (request.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            // Filtre par disponibilité
            if (request.getInStock() != null && request.getInStock()) {
                predicates.add(criteriaBuilder.greaterThan(root.get("stock"), 0));
            }

            // Filtre par produits vedettes
            if (request.getFeatured() != null && request.getFeatured()) {
                predicates.add(criteriaBuilder.equal(root.get("featured"), true));
            }

            // Filtre par produits en promotion
            if (request.getOnSale() != null && request.getOnSale()) {
                predicates.add(criteriaBuilder.equal(root.get("onSale"), true));
            }

            // Filtre par nouveautés
            if (request.getNewArrival() != null && request.getNewArrival()) {
                predicates.add(criteriaBuilder.equal(root.get("newArrival"), true));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
