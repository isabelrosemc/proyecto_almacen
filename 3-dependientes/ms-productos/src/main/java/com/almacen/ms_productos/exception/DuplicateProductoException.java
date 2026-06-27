package com.almacen.ms_productos.exception;

public class DuplicateProductoException
        extends RuntimeException {

    public DuplicateProductoException(String message) {
        super(message);
    }
}