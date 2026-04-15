package com.challenge.project02.features.user.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    String username,
    String email,
    LocalDateTime createdAt
) {
    
}
