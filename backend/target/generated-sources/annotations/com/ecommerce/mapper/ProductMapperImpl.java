package com.ecommerce.mapper;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Product;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:49+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder<?, ?> product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.shortDescription( request.getShortDescription() );
        product.price( request.getPrice() );
        product.oldPrice( request.getOldPrice() );
        product.discountPercent( request.getDiscountPercent() );
        product.stock( request.getStock() );
        product.sku( request.getSku() );
        product.brand( request.getBrand() );
        Set<String> set = request.getColors();
        if ( set != null ) {
            product.colors( new LinkedHashSet<String>( set ) );
        }
        Set<String> set1 = request.getSizes();
        if ( set1 != null ) {
            product.sizes( new LinkedHashSet<String>( set1 ) );
        }
        List<String> list = request.getImages();
        if ( list != null ) {
            product.images( new ArrayList<String>( list ) );
        }
        product.specifications( request.getSpecifications() );
        product.featured( request.isFeatured() );
        product.newArrival( request.isNewArrival() );
        product.onSale( request.isOnSale() );
        product.active( request.isActive() );

        product.viewCount( 0 );
        product.soldCount( 0 );
        product.archived( false );

        return product.build();
    }

    @Override
    public void updateEntity(ProductRequest request, Product product) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setShortDescription( request.getShortDescription() );
        product.setPrice( request.getPrice() );
        product.setOldPrice( request.getOldPrice() );
        product.setDiscountPercent( request.getDiscountPercent() );
        product.setStock( request.getStock() );
        product.setSku( request.getSku() );
        product.setBrand( request.getBrand() );
        if ( product.getColors() != null ) {
            Set<String> set = request.getColors();
            if ( set != null ) {
                product.getColors().clear();
                product.getColors().addAll( set );
            }
            else {
                product.setColors( null );
            }
        }
        else {
            Set<String> set = request.getColors();
            if ( set != null ) {
                product.setColors( new LinkedHashSet<String>( set ) );
            }
        }
        if ( product.getSizes() != null ) {
            Set<String> set1 = request.getSizes();
            if ( set1 != null ) {
                product.getSizes().clear();
                product.getSizes().addAll( set1 );
            }
            else {
                product.setSizes( null );
            }
        }
        else {
            Set<String> set1 = request.getSizes();
            if ( set1 != null ) {
                product.setSizes( new LinkedHashSet<String>( set1 ) );
            }
        }
        if ( product.getImages() != null ) {
            List<String> list = request.getImages();
            if ( list != null ) {
                product.getImages().clear();
                product.getImages().addAll( list );
            }
            else {
                product.setImages( null );
            }
        }
        else {
            List<String> list = request.getImages();
            if ( list != null ) {
                product.setImages( new ArrayList<String>( list ) );
            }
        }
        product.setSpecifications( request.getSpecifications() );
        product.setFeatured( request.isFeatured() );
        product.setNewArrival( request.isNewArrival() );
        product.setOnSale( request.isOnSale() );
        product.setActive( request.isActive() );
    }

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.slug( product.getSlug() );
        productResponse.shortDescription( product.getShortDescription() );
        productResponse.description( product.getDescription() );
        productResponse.specifications( product.getSpecifications() );
        productResponse.price( product.getPrice() );
        productResponse.oldPrice( product.getOldPrice() );
        productResponse.discountPercent( product.getDiscountPercent() );
        productResponse.stock( product.getStock() );
        productResponse.sku( product.getSku() );
        productResponse.brand( product.getBrand() );
        Set<String> set = product.getColors();
        if ( set != null ) {
            productResponse.colors( new LinkedHashSet<String>( set ) );
        }
        Set<String> set1 = product.getSizes();
        if ( set1 != null ) {
            productResponse.sizes( new LinkedHashSet<String>( set1 ) );
        }
        List<String> list = product.getImages();
        if ( list != null ) {
            productResponse.images( new ArrayList<String>( list ) );
        }
        productResponse.featured( product.isFeatured() );
        productResponse.newArrival( product.isNewArrival() );
        productResponse.onSale( product.isOnSale() );
        productResponse.active( product.isActive() );
        productResponse.viewCount( product.getViewCount() );
        productResponse.soldCount( product.getSoldCount() );
        productResponse.createdAt( product.getCreatedAt() );
        productResponse.updatedAt( product.getUpdatedAt() );

        productResponse.discountedPrice( product.getDiscountedPrice() );
        productResponse.inStock( !product.isOutOfStock() );
        productResponse.mainImage( getMainImage(product) );
        productResponse.averageRating( product.getAverageRating() );
        productResponse.reviewCount( product.getReviewCount() );
        productResponse.categoryName( product.getCategory() != null ? product.getCategory().getName() : null );
        productResponse.subcategoryName( product.getSubcategory() != null ? product.getSubcategory().getName() : null );

        return productResponse.build();
    }

    @Override
    public List<ProductResponse> toResponseList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( products.size() );
        for ( Product product : products ) {
            list.add( toResponse( product ) );
        }

        return list;
    }
}
