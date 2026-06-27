package com.almacen.ms_clientes.exception;

public class DuplicateClienteException extends RuntimeException {

    public DuplicateClienteException(String mensaje) {
        super(mensaje);
    }
}