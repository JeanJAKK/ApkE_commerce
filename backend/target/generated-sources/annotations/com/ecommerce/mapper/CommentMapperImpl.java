package com.ecommerce.mapper;

import com.ecommerce.dto.request.CommentRequest;
import com.ecommerce.dto.response.CommentResponse;
import com.ecommerce.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-09T23:14:48+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Ubuntu)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toEntity(CommentRequest request) {
        if ( request == null ) {
            return null;
        }

        Comment.CommentBuilder<?, ?> comment = Comment.builder();

        comment.content( request.getContent() );
        comment.attachment( request.getAttachment() );

        comment.visible( true );
        comment.pinned( false );
        comment.reported( false );

        return comment.build();
    }

    @Override
    public CommentResponse toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.replies( mapReplies( comment.getReplies() ) );
        commentResponse.id( comment.getId() );
        commentResponse.content( comment.getContent() );
        commentResponse.visible( comment.isVisible() );
        commentResponse.pinned( comment.isPinned() );
        commentResponse.attachment( comment.getAttachment() );
        commentResponse.createdAt( comment.getCreatedAt() );

        commentResponse.userName( comment.getUser().getFullName() );
        commentResponse.userAvatar( comment.getUser().getAvatar() );
        commentResponse.likeCount( comment.getLikeCount() );

        return commentResponse.build();
    }

    @Override
    public List<CommentResponse> toResponseList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponse> list = new ArrayList<CommentResponse>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( toResponse( comment ) );
        }

        return list;
    }
}
