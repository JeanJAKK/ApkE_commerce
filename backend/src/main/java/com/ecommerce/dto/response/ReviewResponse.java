package com.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO pour la réponse d'avis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Long productId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private int rating;
    private String title;
    private String content;
    private String image;
    private boolean verified;
    private boolean featured;
    private boolean visible;
    private int helpfulCount;
    private LocalDateTime createdAt;
}
