package com.almacen.ms_proveedores.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorResponseDTO {

    private Long id;

    private String razonSocial;

    private String rut;

    private String email;

    private String telefono;

    private String direccion;

    private Boolean estado;

    private LocalDateTime fechaCreacion;
}