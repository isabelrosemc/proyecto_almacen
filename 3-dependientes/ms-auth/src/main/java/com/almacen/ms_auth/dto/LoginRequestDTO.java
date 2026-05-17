package com.almacen.ms_auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    private String password;
}