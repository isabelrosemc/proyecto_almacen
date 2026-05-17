package com.almacen.ms_auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(
            UnauthorizedException ex
    ) {

        log.error("Error autenticacion: {}",
                ex.getMessage());

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now()
        );

        response.put(
                "message",
                ex.getMessage()
        );

        response.put(
                "status",
                HttpStatus.UNAUTHORIZED.value()
        );

        return ResponseEntity.status(
                HttpStatus.UNAUTHORIZED
        ).body(response);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        log.error("Errores de validacion");

        Map<String, String> errores =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->

                        errores.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity.badRequest()
                .body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(
            Exception ex
    ) {

        log.error("Error interno: {}",
                ex.getMessage());

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now()
        );

        response.put(
                "message",
                "Error interno del servidor"
        );

        response.put(
                "status",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
        ).body(response);
    }
}