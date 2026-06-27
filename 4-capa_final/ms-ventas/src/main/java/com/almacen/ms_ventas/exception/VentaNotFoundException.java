package com.almacen.ms_ventas.exception;

public class VentaNotFoundException
        extends RuntimeException {

    public VentaNotFoundException(String message) {
        super(message);
    }
}