package com.almacen.ms_ventas.exception;

public class StockInsuficienteException
        extends RuntimeException {

    public StockInsuficienteException(String message) {
        super(message);
    }
}