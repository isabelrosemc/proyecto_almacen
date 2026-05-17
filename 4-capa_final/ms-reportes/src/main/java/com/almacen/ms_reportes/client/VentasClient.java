package com.almacen.ms_reportes.client;

import com.almacen.ms_reportes.dto.VentaResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-ventas")
public interface VentasClient {

    @GetMapping("/api/ventas")
    List<VentaResponseDTO> listarVentas();
}