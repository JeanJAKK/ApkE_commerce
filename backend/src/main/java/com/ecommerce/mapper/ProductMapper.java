package com.ecommerce.mapper;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Product;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Product <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", constant = "0")
    @Mapping(target = "soldCount", constant = "0")
    @Mapping(target = "archived", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "favoritedBy", ignore = true)
    Product toEntity(ProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "favoritedBy", ignore = true)
    void updateEntity(ProductRequest request, @MappingTarget Product product);

    @Mapping(target = "discountedPrice", expression = "java(product.getDiscountedPrice())")
    @Mapping(target = "inStock", expression = "java(!product.isOutOfStock())")
    @Mapping(target = "mainImage", expression = "java(getMainImage(product))")
    @Mapping(target = "averageRating", expression = "java(product.getAverageRating())")
    @Mapping(target = "reviewCount", expression = "java(product.getReviewCount())")
    @Mapping(target = "categoryName", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
    @Mapping(target = "subcategoryName", expression = "java(product.getSubcategory() != null ? product.getSubcategory().getName() : null)")
    ProductResponse toResponse(Product product);

    @Mapping(target = "discountedPrice", expression = "java(product.getDiscountedPrice())")
    @Mapping(target = "inStock", expression = "java(!product.isOutOfStock())")
    @Mapping(target = "mainImage", expression = "java(getMainImage(product))")
    @Mapping(target = "averageRating", expression = "java(product.getAverageRating())")
    @Mapping(target = "reviewCount", expression = "java(product.getReviewCount())")
    @Mapping(target = "categoryName", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
    @Mapping(target = "subcategoryName", expression = "java(product.getSubcategory() != null ? product.getSubcategory().getName() : null)")
    List<ProductResponse> toResponseList(List<Product> products);

    default String getMainImage(Product product) {
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            return product.getImages().get(0);
        }
        return null;
    }
}
