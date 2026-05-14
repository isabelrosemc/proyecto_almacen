package com.almacen.ms_stock.exception;

public class DuplicateStockException
        extends RuntimeException {

    public DuplicateStockException(String message) {
        super(message);
    }
}
