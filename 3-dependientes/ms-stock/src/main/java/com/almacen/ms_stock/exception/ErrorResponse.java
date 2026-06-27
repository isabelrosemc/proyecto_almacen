package com.almacen.ms_stock.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String mensaje;

    private int status;

    private LocalDateTime timestamp;
}