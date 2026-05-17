package com.almacen.ms_reportes.service.Impl;

import com.almacen.ms_reportes.client.VentasClient;
import com.almacen.ms_reportes.dto.VentaResponseDTO;
import com.almacen.ms_reportes.service.ReporteService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final VentasClient ventasClient;

    @Override
    public List<VentaResponseDTO> obtenerReporteVentas() {

        return ventasClient.listarVentas();
    }
}