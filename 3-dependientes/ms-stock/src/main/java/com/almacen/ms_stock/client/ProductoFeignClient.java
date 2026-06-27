package com.almacen.ms_stock.client;

import com.almacen.ms_stock.dto.ProductoDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-productos")
public interface ProductoFeignClient {

    @GetMapping("/api/productos/{id}")
    ProductoDTO obtenerProducto(
            @PathVariable Long id
    );
}