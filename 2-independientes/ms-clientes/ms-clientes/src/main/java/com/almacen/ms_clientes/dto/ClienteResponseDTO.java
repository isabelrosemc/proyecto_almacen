package com.almacen.ms_clientes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClienteResponseDTO {

    private Long id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String direccion;
    private Boolean estado;
    private LocalDateTime fechaRegistro;
}