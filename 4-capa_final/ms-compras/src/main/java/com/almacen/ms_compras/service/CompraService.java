package com.almacen.ms_compras.service;

import com.almacen.ms_compras.dto.*;

import java.util.List;

public interface CompraService {

    CompraResponseDTO crearCompra(
            CompraRequestDTO request
    );

    List<CompraResponseDTO> listarCompras();

    CompraResponseDTO buscarPorId(Long id);
}