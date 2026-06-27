package com.almacen.ms_ventas.service;

import com.almacen.ms_ventas.dto.*;

import java.util.List;

public interface VentaService {

    VentaResponseDTO crearVenta(
            VentaRequestDTO request
    );

    List<VentaResponseDTO> listarVentas();

    VentaResponseDTO buscarPorId(Long id);
}