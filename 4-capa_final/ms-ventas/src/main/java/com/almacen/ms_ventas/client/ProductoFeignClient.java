package com.almacen.ms_ventas.client;

import com.almacen.ms_ventas.dto.ProductoDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-productos")
public interface ProductoFeignClient {

    @GetMapping("/api/productos/{id}")
    ProductoDTO obtenerProducto(
            @PathVariable Long id
    );
}