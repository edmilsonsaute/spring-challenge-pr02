package com.challenge.project02.features.user.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3,max = 50,message = "Username deve ter no minimo 3 caracteres e 50 no máximo")
    String username,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser valido")
    String email,

    @NotBlank
    @Size(min = 6,message =  "Username deve ter no minimo 6")
    String password

) {
    
}
