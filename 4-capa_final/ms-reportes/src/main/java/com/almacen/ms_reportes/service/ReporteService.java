package com.almacen.ms_reportes.service;

import com.almacen.ms_reportes.dto.VentaResponseDTO;

import java.util.List;

public interface ReporteService {

    List<VentaResponseDTO> obtenerReporteVentas();
}