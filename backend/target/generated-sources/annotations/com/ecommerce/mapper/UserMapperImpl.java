package com.ecommerce.mapper;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.UpdateUserRequest;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:50+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.firstName( request.getFirstName() );
        user.lastName( request.getLastName() );
        user.email( request.getEmail() );
        user.password( request.getPassword() );
        user.phone( request.getPhone() );
        user.address( request.getAddress() );
        user.city( request.getCity() );
        user.country( request.getCountry() );

        user.blocked( false );
        user.enabled( true );

        return user.build();
    }

    @Override
    public void updateEntity(UpdateUserRequest request, User user) {
        if ( request == null ) {
            return;
        }

        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );
        user.setPhone( request.getPhone() );
        user.setAddress( request.getAddress() );
        user.setCity( request.getCity() );
        user.setCountry( request.getCountry() );
        user.setAvatar( request.getAvatar() );
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.firstName( user.getFirstName() );
        userResponse.lastName( user.getLastName() );
        userResponse.email( user.getEmail() );
        userResponse.phone( user.getPhone() );
        userResponse.address( user.getAddress() );
        userResponse.city( user.getCity() );
        userResponse.country( user.getCountry() );
        userResponse.avatar( user.getAvatar() );
        userResponse.enabled( user.isEnabled() );
        userResponse.blocked( user.isBlocked() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        userResponse.fullName( user.getFullName() );
        userResponse.roles( mapRoles(user.getRoles()) );

        return userResponse.build();
    }
}
