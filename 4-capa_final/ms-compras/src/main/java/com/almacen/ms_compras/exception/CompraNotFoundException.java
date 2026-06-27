package com.almacen.ms_compras.exception;

public class CompraNotFoundException
        extends RuntimeException {

    public CompraNotFoundException(String message) {
        super(message);
    }
}