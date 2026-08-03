package com.ecommerce.mapper;

import com.ecommerce.dto.request.CommentRequest;
import com.ecommerce.dto.response.CommentResponse;
import com.ecommerce.entity.Comment;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper pour les conversions Comment <-> DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "visible", constant = "true")
    @Mapping(target = "pinned", constant = "false")
    @Mapping(target = "reported", constant = "false")
    @Mapping(target = "reportReason", ignore = true)
    @Mapping(target = "likedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Comment toEntity(CommentRequest request);

    @Mapping(target = "userName", expression = "java(comment.getUser().getFullName())")
    @Mapping(target = "userAvatar", expression = "java(comment.getUser().getAvatar())")
    @Mapping(target = "likeCount", expression = "java(comment.getLikeCount())")
    @Mapping(target = "replies", source = "replies", qualifiedByName = "mapReplies")
    CommentResponse toResponse(Comment comment);

    List<CommentResponse> toResponseList(List<Comment> comments);

    @Named("mapReplies")
    default List<CommentResponse> mapReplies(List<Comment> replies) {
        if (replies == null) return null;
        return toResponseList(replies);
    }
}
