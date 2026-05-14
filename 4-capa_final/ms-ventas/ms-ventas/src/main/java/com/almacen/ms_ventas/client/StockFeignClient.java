package com.almacen.ms_ventas.client;

import com.almacen.ms_ventas.dto.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-stock")
public interface StockFeignClient {

    @GetMapping("/api/stock/producto/{productoId}")
    StockDTO obtenerStockPorProducto(
            @PathVariable Long productoId
    );

    @PutMapping("/api/stock/descontar")
    void descontarStock(
            @RequestBody
            DescontarStockDTO request
    );
}