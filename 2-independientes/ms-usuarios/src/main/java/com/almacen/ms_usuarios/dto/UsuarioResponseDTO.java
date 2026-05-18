package com.almacen.ms_usuarios.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String password;

    private Boolean estado;

    private String rol;

    private LocalDateTime fechaCreacion;
    
}