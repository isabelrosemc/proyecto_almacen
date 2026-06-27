package com.almacen.ms_stock.exception;

public class InvalidStockException
        extends RuntimeException {

    public InvalidStockException(String message) {
        super(message);
    }
}
