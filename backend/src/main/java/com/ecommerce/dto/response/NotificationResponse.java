package com.ecommerce.dto.response;

import com.ecommerce.entity.ENotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO pour la réponse de notification
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private String title;
    private String message;
    private ENotificationType type;
    private String typeDisplayName;
    private boolean read;
    private String link;
    private Long relatedId;
    private LocalDateTime createdAt;
}
