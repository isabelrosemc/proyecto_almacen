package com.almacen.ms_detalles_ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-productos")
public interface ProductoClient {

    @GetMapping("/api/productos/{id}")
    Object obtenerProducto(@PathVariable Long id);
}