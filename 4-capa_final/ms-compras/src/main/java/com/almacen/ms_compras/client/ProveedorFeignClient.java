package com.almacen.ms_compras.client;

import com.almacen.ms_compras.dto.ProveedorDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-proveedores")
public interface ProveedorFeignClient {

    @GetMapping("/api/proveedores/{id}")
    ProveedorDTO obtenerProveedor(
            @PathVariable Long id
    );
}