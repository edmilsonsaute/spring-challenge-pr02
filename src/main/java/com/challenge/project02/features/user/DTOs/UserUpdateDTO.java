package com.challenge.project02.features.user.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

    @Size(min = 3,max = 50,message = "Username deve ter no minimo 3 caracteres e 50 no máximo")
    String username,

    @Email(message = "Email deve ser valido")
    String email
) {
    
}
