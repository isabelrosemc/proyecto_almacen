package com.almacen.ms_stock.exception;

public class StockNotFoundException
        extends RuntimeException {

    public StockNotFoundException(String message) {
        super(message);
    }
}