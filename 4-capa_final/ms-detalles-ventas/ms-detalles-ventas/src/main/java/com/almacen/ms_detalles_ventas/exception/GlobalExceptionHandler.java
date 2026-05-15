package com.almacen.ms_detalles_ventas.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DetalleVentaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            DetalleVentaNotFoundException ex) {

        log.error("Detalle no encontrado {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .status(404)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(),
                        error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errores);
    }
}