package com.ecommerce.mapper;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.UpdateUserRequest;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.entity.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper pour les conversions User <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "resetToken", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "resetToken", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toResponse(User user);

    default Set<String> mapRoles(Set<com.ecommerce.entity.Role> roles) {
        if (roles == null) return null;
        return roles.stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toSet());
    }
}
