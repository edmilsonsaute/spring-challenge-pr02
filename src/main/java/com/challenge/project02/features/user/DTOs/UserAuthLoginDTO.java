package com.challenge.project02.features.user.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserAuthLoginDTO(
    
    @NotBlank(message = "Username é obrigatório")
    String username, 

    @NotBlank(message = "Username é obrigatório")
    String password)
    
    {
}
