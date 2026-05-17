package com.almacen.ms_usuarios.dto;

import com.almacen.ms_usuarios.model.enums.RolNombre;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 100)
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 6, max = 255)
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private RolNombre rol;

    private Boolean estado;
}