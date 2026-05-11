package com.almacen.ms_productos.exception;

import feign.FeignException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ProductoNotFoundException ex
    ) {

        log.error("Producto no encontrado: {}",
                ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DuplicateProductoException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateProductoException ex
    ) {

        log.error("Producto duplicado: {}",
                ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(RemoteServiceException.class)
    public ResponseEntity<ErrorResponse> handleRemote(
            RemoteServiceException ex
    ) {

        log.error("Error servicio remoto: {}",
                ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .status(HttpStatus.BAD_GATEWAY.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    handleValidation(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errores =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errores.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        return ResponseEntity
                .badRequest()
                .body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex
    ) {

        log.error("Error interno: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .mensaje("Error interno del servidor")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}