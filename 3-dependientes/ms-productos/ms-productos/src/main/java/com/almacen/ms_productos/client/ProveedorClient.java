package com.almacen.ms_productos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.almacen.ms_productos.dto.ProveedorDTO;

@FeignClient(name = "ms-proveedores")
public interface ProveedorClient {

    @GetMapping("/api/proveedores/{id}")
    ProveedorDTO obtenerProveedor(
            @PathVariable Long id
    );
}