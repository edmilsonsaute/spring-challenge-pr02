package com.challenge.project02.features.user.DTOs;

public record UserAuthResponseDTO(
    String accessToken,
    String refreshToken
) {
    
}
