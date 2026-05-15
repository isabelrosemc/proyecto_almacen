package com.almacen.ms_clientes.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String mensaje;
    private int status;
    private LocalDateTime timestamp;
}