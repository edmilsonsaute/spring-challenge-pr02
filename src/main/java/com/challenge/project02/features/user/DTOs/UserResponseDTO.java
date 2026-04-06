package com.challenge.project02.features.user.DTOs;

import java.time.LocalDateTime;

public record UserResponseDTO(
    String id,
    String username,
    String email,
    LocalDateTime createdAt
) {
    
}
