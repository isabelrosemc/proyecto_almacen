package com.almacen.ms_auth.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private Boolean activo;
}