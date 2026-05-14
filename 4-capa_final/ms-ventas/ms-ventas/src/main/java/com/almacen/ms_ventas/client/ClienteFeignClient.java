package com.almacen.ms_ventas.client;

import com.almacen.ms_ventas.dto.ClienteDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-clientes")
public interface ClienteFeignClient {

    @GetMapping("/api/clientes/{id}")
    ClienteDTO obtenerCliente(
            @PathVariable Long id
    );
}