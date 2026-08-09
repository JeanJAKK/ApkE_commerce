package com.ecommerce.mapper;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.entity.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:49+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder<?, ?> category = Category.builder();

        category.name( request.getName() );
        category.description( request.getDescription() );
        category.image( request.getImage() );
        category.icon( request.getIcon() );
        category.position( request.getPosition() );
        category.active( request.isActive() );

        return category.build();
    }

    @Override
    public void updateEntity(CategoryRequest request, Category category) {
        if ( request == null ) {
            return;
        }

        category.setName( request.getName() );
        category.setDescription( request.getDescription() );
        category.setImage( request.getImage() );
        category.setIcon( request.getIcon() );
        category.setPosition( request.getPosition() );
        category.setActive( request.isActive() );
    }

    @Override
    public CategoryResponse toResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.subcategories( mapSubcategories( category.getSubcategories() ) );
        categoryResponse.id( category.getId() );
        categoryResponse.name( category.getName() );
        categoryResponse.slug( category.getSlug() );
        categoryResponse.description( category.getDescription() );
        categoryResponse.image( category.getImage() );
        categoryResponse.icon( category.getIcon() );
        categoryResponse.position( category.getPosition() );
        categoryResponse.active( category.isActive() );

        categoryResponse.parentName( category.getParent() != null ? category.getParent().getName() : null );
        categoryResponse.productCount( category.getProducts() != null ? category.getProducts().size() : 0 );

        return categoryResponse.build();
    }

    @Override
    public List<CategoryResponse> toResponseList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryResponse> list = new ArrayList<CategoryResponse>( categories.size() );
        for ( Category category : categories ) {
            list.add( toResponse( category ) );
        }

        return list;
    }
}
