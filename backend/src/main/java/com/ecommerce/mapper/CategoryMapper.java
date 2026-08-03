package com.ecommerce.mapper;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.entity.Category;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Category <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subcategories", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subcategories", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(CategoryRequest request, @MappingTarget Category category);

    @Mapping(target = "parentName", expression = "java(category.getParent() != null ? category.getParent().getName() : null)")
    @Mapping(target = "productCount", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    @Mapping(target = "subcategories", source = "subcategories", qualifiedByName = "mapSubcategories")
    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    @Named("mapSubcategories")
    default List<CategoryResponse> mapSubcategories(List<Category> subcategories) {
        if (subcategories == null) return null;
        return toResponseList(subcategories);
    }
}
