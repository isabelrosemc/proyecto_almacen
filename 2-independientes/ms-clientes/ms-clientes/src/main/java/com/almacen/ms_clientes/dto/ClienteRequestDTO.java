package com.almacen.ms_clientes.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100)
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato email invalido")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 8, max = 20)
    private String telefono;

    @Size(max = 255)
    private String direccion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}