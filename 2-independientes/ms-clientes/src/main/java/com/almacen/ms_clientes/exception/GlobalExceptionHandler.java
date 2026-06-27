package com.almacen.ms_clientes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ClienteNotFoundException ex) {

        log.error("Cliente no encontrado: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .status(404)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateClienteException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateClienteException ex) {

        log.error("Cliente duplicado: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .status(409)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        log.error("Error de validacion");

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errores);
    }
}