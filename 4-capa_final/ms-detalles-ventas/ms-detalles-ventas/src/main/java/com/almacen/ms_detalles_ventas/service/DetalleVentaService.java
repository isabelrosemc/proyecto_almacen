package com.almacen.ms_detalles_ventas.service;

import com.almacen.ms_detalles_ventas.dto.*;

import java.util.List;

public interface DetalleVentaService {

    DetalleVentaResponseDTO crear(
            DetalleVentaRequestDTO dto);

    List<DetalleVentaResponseDTO> listar();

    DetalleVentaResponseDTO buscarPorId(Long id);

    void eliminar(Long id);
}