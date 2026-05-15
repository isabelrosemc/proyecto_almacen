package com.almacen.ms_detalles_ventas.exception;

public class DetalleVentaNotFoundException
        extends RuntimeException {

    public DetalleVentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}