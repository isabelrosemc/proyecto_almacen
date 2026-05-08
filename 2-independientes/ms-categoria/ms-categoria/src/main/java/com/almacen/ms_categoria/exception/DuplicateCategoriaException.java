package com.almacen.ms_categoria.exception;

public class DuplicateCategoriaException extends RuntimeException {

    public DuplicateCategoriaException(String message) {
        super(message);
    }
}