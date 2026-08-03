package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO pour la réponse de commentaire
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private Long productId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Long parentId;
    private List<CommentResponse> replies;
    private boolean visible;
    private boolean pinned;
    private int likeCount;
    private String attachment;
    private LocalDateTime createdAt;
}
