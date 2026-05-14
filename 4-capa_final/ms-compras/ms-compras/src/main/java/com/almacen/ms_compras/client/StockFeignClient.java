package com.almacen.ms_compras.client;

import com.almacen.ms_compras.dto.ActualizarStockDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-stock")
public interface StockFeignClient {

    @PutMapping("/api/stock/ingresar")
    void ingresarStock(
            @RequestBody
            ActualizarStockDTO request
    );
}