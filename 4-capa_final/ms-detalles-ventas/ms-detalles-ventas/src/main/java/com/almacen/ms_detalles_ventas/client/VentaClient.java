package com.almacen.ms_detalles_ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-ventas")
public interface VentaClient {

    @GetMapping("/api/ventas/{id}")
    Object obtenerVenta(@PathVariable Long id);
}